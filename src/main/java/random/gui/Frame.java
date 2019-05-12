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
import random.Util;
import random.ValidateException;
import random.inspector.EntropyInspector;
import random.inspector.MovingAverageInspector;
import random.stego.Steganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

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
    private int imageWidth;
    private int imageHeight;

    @FXML
    public void initialize() {
        Util.makeOnlyDigitsField(startWritePixel);
        Util.makeOnlyDigitsField(intervalStart);
        Util.makeOnlyDigitsField(intervalEnd);
        Util.makeOnlyDigitsField(interval);
    }

    @FXML
    private void openImageAction() throws IOException, InterruptedException {
        openChooseImageDialog();
    }

    private void openChooseImageDialog() throws IOException, InterruptedException {
        FileChooser fileChooser = Util.getOpenFileChooser("Open image");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }
        originalImage = ImageIO.read(file);
        setOriginalImageForShow();

        imageWidth = originalImage.getWidth();
        imageHeight = originalImage.getHeight();
        imagePixels = new int[imageWidth * imageHeight];
        imagePixels = Steganography.getPixelsFromImage(originalImage);

        originalImagePane.widthProperty().addListener(
                (observableValue, oldSceneWidth, newSceneWidth) -> setOriginalImageForShow());
        originalImagePane.heightProperty().addListener(
                (observableValue, oldSceneWidth, newSceneWidth) -> setOriginalImageForShow());

        statusBar.setText("In the image " + imagePixels.length + " pixels");
    }

    @FXML
    private void extractHiddenMessage() {
        try {
            message.setText(Steganography.extractHiddenMessage(imagePixels, getStartPixel()));
        } catch (ValidateException e) {
            LOG.info(e.getMessage());
            statusBar.setText(e.getMessage());
        }
    }

    private int getStartPixel() {
        try {
            return Integer.parseInt(startWritePixel.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int getIntervalStart() {
        try {
            return Integer.parseInt(intervalStart.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int getIntervalEnd() {
        try {
            return Integer.parseInt(intervalEnd.getText());
        } catch (NumberFormatException e) {
            return imagePixels.length;
        }
    }

    private int getInterval() {
        try {
            return Integer.parseInt(interval.getText());
        } catch (NumberFormatException e) {
            return imagePixels.length / 100;
        }
    }

    private void setOriginalImageForShow() {
        setImageForShow(originalImage, originalImagePane, originalImageForShow);
    }

    private void setNewImageForShow() {
        setImageForShow(newImage, newImagePane, newImageForShow);
    }

    private void setImageForShow(BufferedImage imageFromFile, AnchorPane imagePane, ImageView newImageForShow) {
        BufferedImage scaledImage
                = Util.scale(imageFromFile, (int) imagePane.getWidth(), (int) imagePane.getHeight());
        Image image = SwingFXUtils.toFXImage(scaledImage, null);
        newImageForShow.setImage(image);
    }

    @FXML
    private void insertHiddenMessage() {
        statusBar.setText("");
        String msg = message.getText();
        try {
            newImage = Steganography.insertHiddenMessage(imagePixels, msg, getStartPixel(), imageWidth, imageHeight);
            setNewImageForShow();

            newImagePane.widthProperty().addListener(
                    (observableValue, oldSceneWidth, newSceneWidth) -> setNewImageForShow());
            newImagePane.heightProperty().addListener(
                    (observableValue, oldSceneWidth, newSceneWidth) -> setNewImageForShow());
        } catch (ValidateException e) {
            LOG.info(e.getMessage());
            statusBar.setText(e.getMessage());
        }
    }

    @FXML
    private void openSaveImageDialog() throws IOException {
        if (newImage == null) {
            statusBar.setText(NO_IMAGE_WARNING);
            return;
        }
        FileChooser fileChooser = Util.getSaveFileChooser("Save image");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            ImageIO.write(newImage, "bmp", file);
            statusBar.setText("Image was saved");
        }
    }

    @FXML
    private void convertToBmp() throws IOException {
        if (originalImage == null) {
            statusBar.setText(NO_IMAGE_WARNING);
            return;
        }
        FileChooser fileChooser = Util.getSaveFileChooser("Save image");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            ImageIO.write(originalImage, "bmp", file);
            statusBar.setText("Image was converted");
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

    private boolean notValidChartParams() {
        if (imagePixels == null) {
            statusBar.setText(NO_IMAGE_WARNING);
            return true;
        }
        if (getIntervalStart() > getIntervalEnd()) {
            statusBar.setText("Wrong interval");
            return true;
        }
        if (getIntervalEnd() > imagePixels.length) {
            statusBar.setText(String.format("Invalid interval. In the image %s pixels", imagePixels.length));
            return true;
        }
        if ((getIntervalEnd() - getIntervalStart()) / getInterval() < 10) {
            statusBar.setText("Check interval too large");
            return true;
        }
        return false;
    }

    @FXML
    private void doLastBitImage() {
        if (imagePixels == null) {
            statusBar.setText(NO_IMAGE_WARNING);
            return;
        }
        newImage = Steganography.doLastBitImage(imagePixels, imageWidth, imageHeight);
        setNewImageForShow();
        newImagePane.widthProperty().addListener(
                (observableValue, oldSceneWidth, newSceneWidth) -> setNewImageForShow());
        newImagePane.heightProperty().addListener(
                (observableValue, oldSceneWidth, newSceneWidth) -> setNewImageForShow());
    }

    @FXML
    private void exit() {
        Platform.exit();
        System.exit(0);
    }
}
