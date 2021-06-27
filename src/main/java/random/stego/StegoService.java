package random.stego;

import random.ValidateException;
import random.utils.Utils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static java.nio.charset.StandardCharsets.UTF_8;

public class StegoService {

    private static final int BYTES_IN_INT = 4;

    private StegoService() {
    }

    /**
     * Insert message to image pixel array
     *
     * @param pixels             image pixel array
     * @param message            message
     * @param startPixelPosition start pixel to write
     * @param imageWidth         image width
     * @param imageHeight        image height
     * @return image with hidden message
     */
    public static BufferedImage insertHiddenMessage(
            int[] pixels, String message, int startPixelPosition, int imageWidth, int imageHeight)
            throws ValidateException {
        if (message == null || message.equals("")) {
            throw new ValidateException("Enter text");
        }
        if (pixels == null) {
            throw new ValidateException("Select a file");
        }
        if (startPixelPosition > pixels.length) {
            throw new ValidateException("Wrong pixel number");
        }

        byte[] messageBytes = message.getBytes(UTF_8);
        int messageBytesLength = messageBytes.length;
        if (messageBytesLength > (pixels.length - (startPixelPosition + BYTES_IN_INT))) {
            throw new ValidateException("Not enough space to write");
        }

        byte[] lengthBytes = ByteBuffer.allocate(4).putInt(messageBytesLength).array();
        byte[] resultBytes = Utils.concatenateByteArrays(lengthBytes, messageBytes);
        for (int i = 0; i < resultBytes.length; startPixelPosition++, i++) {
            pixels[startPixelPosition] = insertMessageByteToPixel(resultBytes[i], pixels[startPixelPosition]);
        }

        BufferedImage imageWithMessage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        imageWithMessage.setRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
        return imageWithMessage;
    }

    /**
     * Extract message from image
     *
     * @param pixels           image pixels
     * @param msgPixelPosition start pixel number of message
     * @return message from image
     */
    public static String extractHiddenMessage(int[] pixels, int msgPixelPosition) throws ValidateException {
        if (pixels == null) {
            throw new ValidateException("Select a file");
        }

        byte[] lengthBytes = new byte[BYTES_IN_INT];
        for (int i = 0; i < BYTES_IN_INT; i++) {
            lengthBytes[i] = extractMsgByteFromPixel(pixels[i]);
        }
        int messageBytesLength = ByteBuffer.wrap(lengthBytes).getInt();
        byte[] extractedMessageBytes = new byte[messageBytesLength];
        int position = msgPixelPosition + BYTES_IN_INT;
        for (int i = 0; i < messageBytesLength; position++, i++) {
            extractedMessageBytes[i] = extractMsgByteFromPixel(pixels[position]);
        }
        return new String(extractedMessageBytes, UTF_8);
    }

    /**
     * Insert message byte to pixel
     *
     * @param msgByte message item (one byte)
     * @param pixel   image pixel (three bytes)
     * @return pixel with message byte
     */
    private static int insertMessageByteToPixel(byte msgByte, int pixel) {
        int pixelMask = pixel & 0xfc_f8_f8; // 11111100_11111000_11111000
        //            0xc0 = 11000000           0x38 = 00111000          0x7 = 00000111
        int msgMask = ((msgByte & 0xc0) << 10) | ((msgByte & 0x38) << 5) | (msgByte & 0x7);
        return pixelMask | msgMask;
    }

    /**
     * Extracts message byte from image bytes
     *
     * @param pixel pixel with message
     * @return message byte
     */
    public static byte extractMsgByteFromPixel(int pixel) {
        //               0xc0 = 11000000         0x38 = 00111000      0x7 = 00000111
        return (byte) (((pixel >> 10) & 0xc0) | ((pixel >> 5) & 0x38) | (pixel & 0x7));
    }
}
