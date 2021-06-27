package random.inspector;

import java.awt.image.BufferedImage;

public class LastBitService {
    /**
     * Generates an image based on the last bits
     *
     * @param pixels      image pixel array
     * @param imageWidth  image width
     * @param imageHeight image height
     * @return image based on the last bits
     */
    public static BufferedImage getLastBitImage(int[] pixels, int imageWidth, int imageHeight) {
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
            pixels[i] = pixels[i] & 0x03_07_07; // 00000011_00000111_00000111
        }
        return pixels;
    }
}
