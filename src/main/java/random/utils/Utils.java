package random.utils;

import javafx.scene.control.TextField;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private Utils() {
    }

    public static void makeOnlyDigitsField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static byte[] concatenateByteArrays(byte[]... bytes) {
        if (bytes == null || bytes.length == 0) {
            return new byte[0];
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            for (byte[] b : bytes) {
                outputStream.write(b);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
        return new byte[0];
    }

    public static int parseInt(String textToParse, int defaultValue) {
        try {
            return Integer.parseInt(textToParse);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
