package random.gui;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import random.ValidateException;
import random.inspector.EntropyInspector;
import random.inspector.LastBitService;
import random.inspector.MovingAverageInspector;
import random.stego.StegoService;
import random.utils.FileChooserUtils;
import random.utils.ImageService;
import random.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static random.utils.Utils.parseInt;

public class Frame {

    private static final Logger LOG = Logger.getLogger(Frame.class.getName());
    private static final String NO_IMAGE_WARNING = "You need to open image";

    public TextField startWritePixel;
    public TextArea message;
    public Label statusBar;
    public ImageView originalImageForShow;
    public ImageView newImageForShow;
    public AnchorPane originalImagePane;
    public AnchorPane newImagePane;
    public TextField intervalStart;
    public TextField intervalEnd;
    public TextField interval;

    private BufferedImage originalImage;
    private BufferedImage newImage;

    private int[] imagePixels;

    @FXML
    public void initialize() {
        Utils.makeOnlyDigitsField(startWritePixel);
        Utils.makeOnlyDigitsField(intervalStart);
        Utils.makeOnlyDigitsField(intervalEnd);
        Utils.makeOnlyDigitsField(interval);
    }

    @FXML
    private void openImageAction() {
        FileChooser fileChooser = FileChooserUtils.getOpenFileChooser("Open image");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) {
            setStatus("No file selected");
            return;
        }
        try {
            originalImage = ImageIO.read(file);
            imagePixels = ImageService.getPixelsFromImage(originalImage);

            setupImage(originalImage, originalImageForShow, originalImagePane);
            setStatus("In the image " + imagePixels.length + " pixels");
        } catch (IOException | InterruptedException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            setStatus("Sorry, cannot open the file");
        }

        message.setText("");
        newImageForShow.setImage(null);
    }

    @FXML
    private void extractHiddenMessage() {
        try {
            message.setText(StegoService.extractHiddenMessage(imagePixels, getStartPixel()));
        } catch (ValidateException e) {
            LOG.info(e.getMessage());
            setStatus(e.getMessage());
        }
    }

    @FXML
    private void insertHiddenMessage() {
        setStatus("");
        String msg = message.getText();
        if (originalImage == null) {
            setStatus("Select the image");
            return;
        }
        try {
            newImage = StegoService.insertHiddenMessage(imagePixels, msg, getStartPixel(),
                    originalImage.getWidth(), originalImage.getHeight());
            setupImage(newImage, newImageForShow, newImagePane);
        } catch (ValidateException e) {
            LOG.info(e.getMessage());
            setStatus(e.getMessage());
        }
    }

    @FXML
    private void openSaveImageDialog() {
        if (newImage == null) {
            setStatus(NO_IMAGE_WARNING);
            return;
        }
        FileChooser fileChooser = FileChooserUtils.getSaveFileChooser("Save image");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                ImageIO.write(newImage, "bmp", file);
                setStatus("Image was saved");
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.getMessage());
                setStatus("Sorry, cannot save the file");
            }
        }
    }

    @FXML
    private void convertToBmp() {
        if (originalImage == null) {
            setStatus(NO_IMAGE_WARNING);
            return;
        }
        FileChooser fileChooser = FileChooserUtils.getSaveFileChooser("Save image");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                ImageIO.write(originalImage, "bmp", file);
                setStatus("Image was converted");
            } catch (IOException e) {
                setStatus("Cannot convert the image to BMP");
            }
        }
    }

    @FXML
    private void doAvgChart() {
        if (notValidChartParams()) {
            return;
        }
        MovingAverageInspector.showMovingAverageChart(imagePixels, getIntervalStart(), getIntervalEnd(), getInterval());
    }

    @FXML
    private void doEntropyChart() {
        if (notValidChartParams()) {
            return;
        }
        EntropyInspector.showEntropyCalculationChart(imagePixels, getIntervalStart(), getIntervalEnd(), getInterval());
    }

    @FXML
    private void doLastBitImage() {
        if (imagePixels == null) {
            setStatus(NO_IMAGE_WARNING);
            return;
        }
        newImage = LastBitService.getLastBitImage(imagePixels, originalImage.getWidth(), originalImage.getHeight());
        setupImage(newImage, newImageForShow, newImagePane);
    }

    @FXML
    private void exit() {
        Platform.exit();
        System.exit(0);
    }

    private void setupImage(BufferedImage image, ImageView imageToShow, AnchorPane imagePane) {
        setImageToShow(image, imageToShow, (int) imagePane.getWidth(), (int) imagePane.getHeight());
        imagePane.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> setImageToShow(
                image, imageToShow, (int) imagePane.getWidth(), (int) imagePane.getHeight()));
        imagePane.heightProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> setImageToShow(
                image, imageToShow, (int) imagePane.getWidth(), (int) imagePane.getHeight()));
    }

    private void setImageToShow(BufferedImage imageFromFile, ImageView newImageForShow, int width, int height) {
        BufferedImage scaledImage = ImageService.scale(imageFromFile, width, height);
        Image image = SwingFXUtils.toFXImage(scaledImage, null);
        newImageForShow.setImage(image);
    }

    private int getStartPixel() {
        return parseInt(startWritePixel.getText(), 0);
    }

    private int getIntervalStart() {
        return parseInt(intervalStart.getText(), 0);
    }

    private int getIntervalEnd() {
        return parseInt(intervalEnd.getText(), imagePixels.length);
    }

    private int getInterval() {
        return parseInt(interval.getText(), imagePixels.length / 100);
    }

    private boolean notValidChartParams() {
        if (imagePixels == null) {
            setStatus(NO_IMAGE_WARNING);
            return true;
        }
        if (getIntervalStart() > getIntervalEnd()) {
            setStatus("Wrong interval");
            return true;
        }
        if (getIntervalEnd() > imagePixels.length) {
            setStatus(String.format("Invalid interval. In the image %s pixels", imagePixels.length));
            return true;
        }
        if ((getIntervalEnd() - getIntervalStart()) / getInterval() < 10) {
            setStatus("Check interval too large");
            return true;
        }
        return false;
    }

    private void setStatus(String message) {
        statusBar.setText(message);
    }
}
