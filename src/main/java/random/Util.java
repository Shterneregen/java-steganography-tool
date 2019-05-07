package random;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Util {

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
}
