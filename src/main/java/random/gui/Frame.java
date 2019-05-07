package random.gui;

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
import random.imageinspection.ImageInspection;
import random.stego.Steganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
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

    private FileChooser.ExtensionFilter extFilterBmp
            = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");

    private int[] imagePixels;
    private int imageWidth;
    private int imageHeight;

    @FXML
    public void initialize() {
        makeOnlyDigitsField(startWritePixel);
        makeOnlyDigitsField(intervalStart);
        makeOnlyDigitsField(intervalEnd);
        makeOnlyDigitsField(interval);
    }

    private void makeOnlyDigitsField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void openImageAction() throws IOException, InterruptedException {
        openChooseImageDialog();
    }

    private void openChooseImageDialog() throws IOException, InterruptedException {
        FileChooser fileChooser = initFileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        originalImage = ImageIO.read(file);
        setOriginalImageForShow();

        imageWidth = originalImage.getWidth(null);
        imageHeight = originalImage.getHeight(null);
        imagePixels = new int[imageWidth * imageHeight];
        imagePixels = Steganography.imgToPix(originalImage, imageWidth, imageHeight);

        originalImagePane.widthProperty().addListener(
                (observableValue, oldSceneWidth, newSceneWidth) -> setOriginalImageForShow());
        originalImagePane.heightProperty().addListener(
                (observableValue, oldSceneWidth, newSceneWidth) -> setOriginalImageForShow());

        statusBar.setText("In the image " + imagePixels.length + " pixels");
    }

    @FXML
    private void extractHiddenMessage() {
        message.setText(Steganography.extractHiddenMessage(imagePixels, getStartPixel()));
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
        } catch (NullPointerException | UnsupportedEncodingException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            statusBar.setText("Cannot insert message to image");
        }
    }

    @FXML
    private void openSaveImageDialog() throws IOException {
        if (newImage == null) {
            statusBar.setText(NO_IMAGE_WARNING);
            return;
        }
        FileChooser fileChooser = initFileChooser();
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            ImageIO.write(newImage, "bmp", file);
            statusBar.setText("Image saved");
        }
    }

    private FileChooser initFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilterBmp);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setTitle("Open Resource File");
        return fileChooser;
    }

    @FXML
    private void doAvgChart() {
        if (!validChartParams()) {
            return;
        }
        ImageInspection.showMovingAverageChart(imagePixels, getIntervalStart(), getIntervalEnd(), getInterval());
    }

    @FXML
    private void doEntropyChart() {
        if (!validChartParams()) {
            return;
        }
        ImageInspection.showEntropyCalculationChart(imagePixels, getIntervalStart(), getIntervalEnd(), getInterval());
    }

    private boolean validChartParams() {
        if (imagePixels == null) {
            statusBar.setText(NO_IMAGE_WARNING);
            return false;
        }
        if (getIntervalStart() > getIntervalEnd()) {
            statusBar.setText("Wrong interval");
            return false;
        }
        if (getIntervalEnd() > imagePixels.length) {
            statusBar.setText("Invalid interval. In the image " + imagePixels.length + " pixels");
            return false;
        }
        if ((getIntervalEnd() - getIntervalStart()) / getInterval() < 10) {
            statusBar.setText("Check interval too large");
            return false;
        }
        return true;
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
}
