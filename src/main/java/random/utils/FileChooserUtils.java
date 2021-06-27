package random.utils;

import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserUtils {

    private static final FileChooser.ExtensionFilter EXT_FILTER_BMP
            = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
    private static final FileChooser.ExtensionFilter EXT_FILTER_IMAGES
            = new FileChooser.ExtensionFilter("IMAGE files", "*.png", "*.bmp", "*.jpg", "*.jpeg");

    private FileChooserUtils() {
    }

    public static FileChooser getSaveFileChooser(String title) {
        FileChooser fileChooser = initFileChooser(title);
        fileChooser.getExtensionFilters().add(EXT_FILTER_BMP);
        return fileChooser;
    }

    public static FileChooser getOpenFileChooser(String title) {
        FileChooser fileChooser = initFileChooser(title);
        fileChooser.getExtensionFilters().add(EXT_FILTER_IMAGES);
        return fileChooser;
    }

    private static FileChooser initFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setTitle(title);
        return fileChooser;
    }
}
