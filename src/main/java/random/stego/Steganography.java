package random.stego;

import random.ValidateException;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.nio.charset.StandardCharsets;

public class Steganography {

    private Steganography() {
    }

    /**
     * Generates an image based on the last bits
     *
     * @param pixels      image pixel array
     * @param imageWidth  image width
     * @param imageHeight image height
     * @return image based on the last bits
     */
    public static BufferedImage doLastBitImage(int[] pixels, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, imageWidth, imageHeight, formLastBitsPixelArray(pixels), 0, imageWidth);
        return image;
    }

    /**
     * Generates an array of pixels based on the last significant bits only.
     * The remaining pixels are set to zero.
     *
     * @param pixels image pixel array
     * @return image pixel array only with last significant bits
     */
    public static int[] formLastBitsPixelArray(int[] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = pixels[i] & 0x030707;
        }
        return pixels;
    }

    /**
     * Get pixel array from image
     *
     * @param image image
     * @return pixel array
     */
    public static int[] getPixelsFromImage(BufferedImage image) throws InterruptedException {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int[] pixels = new int[imageWidth * imageHeight];
        PixelGrabber pgr = new PixelGrabber(
                image,
                0, //  (x) coordinates of the cut rectangular area
                0, //  (y) on the basis of which an array of pixels is built
                imageWidth, // Width
                imageHeight, // Height
                pixels,
                0, // the offset in the array, starting from which the first pixel data will be written
                imageWidth); // distance between pixels rows in an array
        pgr.grabPixels();
        return pixels;
    }

    /**
     * Insert message to image pixel array
     *
     * @param pixels      image pixel array
     * @param message     message
     * @param posPix      start pixel to write
     * @param imageWidth  image width
     * @param imageHeight image height
     * @return image with hidden message
     */
    public static BufferedImage insertHiddenMessage(int[] pixels,
                                                    String message,
                                                    int posPix,
                                                    int imageWidth,
                                                    int imageHeight
    ) throws ValidateException {
        if (message.equals("")) {
            throw new ValidateException("Insert message");
        }
        if (pixels == null) {
            throw new ValidateException("Select a file");
        }
        if (posPix > pixels.length) {
            throw new ValidateException("Wrong pixel number");
        }
        // Converts message to integer array
        byte[] intMsg = stringToByteArray(message);
        // Converts check characters to integer array
        byte[] intCheck1 = stringToByteArray("~");
        byte[] intCheck2 = stringToByteArray("^");
        int tempPosPix = posPix;

        if (intMsg.length > (pixels.length - tempPosPix - 8)) {
            throw new ValidateException("Not enough space to write");
        }
        // Check characters
        pixels[tempPosPix] = insertMessageByteToPixel(intCheck1[0], pixels[tempPosPix]);
        tempPosPix++;
        pixels[tempPosPix] = insertMessageByteToPixel(intCheck1[0], pixels[tempPosPix]);
        tempPosPix++;
        for (int j = 0; j < intMsg.length + 1; tempPosPix++, j++) {
            // If the end of the message is reached, then check characters are written
            // they allow to determine the message end
            if (j == intMsg.length) {
                pixels[tempPosPix] = insertMessageByteToPixel(intCheck1[0], pixels[tempPosPix]);
                tempPosPix += 7;
                pixels[tempPosPix] = insertMessageByteToPixel(intCheck2[0], pixels[tempPosPix]);
                break;
            }
            // message element is written to pixel
            pixels[tempPosPix] = insertMessageByteToPixel(intMsg[j], pixels[tempPosPix]);
        }
        BufferedImage retImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        retImg.setRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
        return retImg;
    }

    /**
     * Insert message byte to pixel
     *
     * @param msgByte message item (one byte)
     * @param pixel   image pixel (three bytes)
     * @return pixel with message byte
     */
    private static int insertMessageByteToPixel(byte msgByte, int pixel) {
        int pixelMask = pixel & 0xfcf8f8; // 0xfcf8f8 11111100 11111000 11111000
        //            0xc0 = 11000000           0x38 = 00111000          0x7 = 00000111
        int msgMask = ((msgByte & 0xc0) << 10) | ((msgByte & 0x38) << 5) | msgByte & 0x7;
        return pixelMask | msgMask;
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
        byte[] extMsg = new byte[pixels.length];
        int tempPosPix = msgPixelPosition + 2;
        String resultStr = null;
        int index;
        for (; tempPosPix < pixels.length; tempPosPix++) {
            extMsg[tempPosPix] = extractMsgByteFromPixel(pixels[tempPosPix]);
        }
        String s = byteArrayToString(extMsg);
        index = s.indexOf('~');
        if (s.charAt(index + 7) == '^') {
            resultStr = s.substring(msgPixelPosition + 2, index);
        }
        return resultStr;
    }

    /**
     * Extracts message bits from image bits
     *
     * @param pixel pixel with message
     * @return message byte
     */
    public static byte extractMsgByteFromPixel(int pixel) {
        //               0xc0 = 11000000         0x38 = 00111000      0x7 = 00000111
        return (byte) (((pixel >> 10) & 0xc0) | ((pixel >> 5) & 0x38) | (pixel & 0x7));
    }

    private static byte[] stringToByteArray(String message) {
        return message.getBytes(StandardCharsets.UTF_8);
    }

    private static String byteArrayToString(byte[] byteArrayMsg) {
        return new String(byteArrayMsg, StandardCharsets.UTF_8);
    }
}
