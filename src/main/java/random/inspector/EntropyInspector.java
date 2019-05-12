package random.inspector;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import random.stego.Steganography;

import javax.swing.*;

public class EntropyInspector {

    private EntropyInspector() {
    }

    public static void showEntropyCalculationChart(int[] pixels,
                                                   int intervalStart,
                                                   int intervalEnd,
                                                   int interval) {

        Coordinates coordinates = entropy(pixels, intervalStart, intervalEnd, interval);
        coordinates = Coordinates.validateCoordinates(coordinates);

        XYChart chart = QuickChart
                .getChart("Pixel entropy", "Pixel number", "Entropy", "y(x)",
                        coordinates.getX(), coordinates.getY());
        JFrame chartFrame = new SwingWrapper<>(chart).displayChart("Pixel entropy");
        SwingUtilities.invokeLater(() -> chartFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE));
    }

    /**
     * @param pixels        array of pixels
     * @param intervalStart start point of the check interval
     * @param intervalEnd   end point of the check interval
     * @param interval      interval
     */
    private static Coordinates entropy(int[] pixels,
                                       int intervalStart,
                                       int intervalEnd,
                                       int interval) {

        double[] xData = new double[(pixels.length / interval) + 2];
        double[] yData = new double[(pixels.length / interval) + 2];

        double sumPix1 = 0;
        double sumPix0 = 0;
        double sumBit1 = 0;
        double sumBit0 = 0;
        double tempX = 0;
        double p1;
        double p0;
        double h;

        int[] extMsg = getLeastSignificantPixelArray(pixels);

        for (int i = intervalStart, ii = 0, k = 0; i < intervalEnd; i++) {
            for (int j = 0; j < 8; j++) {
                int oneBit = (extMsg[i] >>> j) & 0x1;
                if (oneBit == 0x1) {
                    sumBit1++;
                } else {
                    sumBit0++;
                }
            }

            sumPix1 += sumBit1;
            sumPix0 += sumBit0;

            if ((ii % interval == 0) || (i == intervalEnd - 2)) {
                tempX += interval;
                xData[k] = tempX;
                p1 = sumPix1 / (sumPix1 + sumPix0);
                p0 = (sumPix0 / (sumPix1 + sumPix0));

                // log a (b) = log c (b) / log c (a)
                h = -((p0 * (Math.log(p0) / Math.log(2))) + (p1 * (Math.log(p1) / Math.log(2))));
                yData[k] = h;
                sumPix1 = 0;
                sumBit1 = 0;
                sumPix0 = 0;
                sumBit0 = 0;
                k++;
            }
            ii++;
        }
        return new Coordinates(xData, yData);
    }

    private static int[] getLeastSignificantPixelArray(int[] pixels) {
        int[] extMsg = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            extMsg[i] = Steganography.extractMsgByteFromPixel(pixels[i]);
        }
        return extMsg;
    }
}
