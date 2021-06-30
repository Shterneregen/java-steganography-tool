package random.utils;

import javafx.scene.control.TextField;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Utils {

    private Utils() {
    }

    public static void makeOnlyDigitsField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static byte[] concatenateByteArrays(byte[]... bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException("Wrong arguments for byte concatenation (null or empty)");
        }
        if (bytes.length == 1) {
            return bytes[0];
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            for (byte[] b : bytes) {
                if (b == null) {
                    continue;
                }
                outputStream.write(b);
            }
            return outputStream.toByteArray();
        }
    }

    public static int parseInt(String textToParse, int defaultValue) {
        try {
            return Integer.parseInt(textToParse);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
