package random.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

public class ImageService {

    private ImageService() {
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


    public static BufferedImage scale(BufferedImage image, int containerWidth, int containerHeight) {
        containerWidth = containerWidth <= 0 ? 1 : containerWidth;
        containerHeight = containerHeight <= 0 ? 1 : containerHeight;
        BufferedImage dst = new BufferedImage(containerWidth, containerHeight, BufferedImage.TYPE_INT_RGB);
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
}
