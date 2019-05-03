package random;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Util {
    // Подгоняет изображение под размеры панели программы.
    public static BufferedImage scale(BufferedImage im, int width, int height) {
        Image image2 = im.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        BufferedImage changedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = changedImage.createGraphics();
        g2d.drawImage(image2, 0, 0, null);
        g2d.dispose();
        return changedImage;
    }
}
