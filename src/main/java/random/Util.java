package random;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Util {

    private Util() {
    }

    private static FileChooser.ExtensionFilter extFilterBmp
            = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
    private static FileChooser.ExtensionFilter extFilterImages
            = new FileChooser.ExtensionFilter("IMAGE files",
            "*.png", "*.bmp", "*.jpg", "*.jpeg");

    public static BufferedImage scale(BufferedImage image, int containerWidth, int containerHeight) {
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage dst = new BufferedImage(containerWidth, containerHeight, type);
        Graphics2D g2 = dst.createGraphics();
        g2.clearRect(0, 0, containerWidth, containerHeight);
        double xScale = (double) containerWidth / image.getWidth();
        double yScale = (double) containerHeight / image.getHeight();

        double scale = Math.min(xScale, yScale);
        int scaledImgWidth = (int) (scale * image.getWidth());
        int scaledImgHeight = (int) (scale * image.getHeight());
        int x = (containerWidth - scaledImgWidth) / 2;
        int y = (containerHeight - scaledImgHeight) / 2;
        g2.drawImage(image, x, y, scaledImgWidth, scaledImgHeight, null);
        g2.dispose();
        return dst;
    }

    public static FileChooser getSaveFileChooser(String title) {
        FileChooser fileChooser = initFileChooser(title);
        fileChooser.getExtensionFilters().add(extFilterBmp);
        return fileChooser;
    }

    public static FileChooser getOpenFileChooser(String title) {
        FileChooser fileChooser = initFileChooser(title);
        fileChooser.getExtensionFilters().add(extFilterImages);
        return fileChooser;
    }

    private static FileChooser initFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setTitle(title);
        return fileChooser;
    }

    public static void makeOnlyDigitsField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
