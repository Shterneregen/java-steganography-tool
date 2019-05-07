package random.imageinspection;

import random.stego.Steganography;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class ImageInspection {

    public static void showMovingAverageChart(int[] pixels, int intervalStart, int intervalEnd, int interval) {
        double[] xData = new double[(pixels.length / interval) + 2];
        double[] yData = new double[(pixels.length / interval) + 2];
        double[] xDataNew = new double[xData.length - 2];
        double[] yDataNew = new double[yData.length - 2];

        arrayOfAverageValue(Steganography.extractLastBits(pixels), intervalStart, intervalEnd, interval, xData, yData);

        for (int i = 0; i < xDataNew.length; i++) {
            xDataNew[i] = xData[i];
            yDataNew[i] = yData[i];
            if (xDataNew[i] == 0 & yDataNew[i] == 0) {
                xDataNew[i] = xDataNew[i - 1];
                yDataNew[i] = yDataNew[i - 1];
            }
            if (xDataNew[i] == 0) {
                xDataNew[i] = xDataNew[i - 1];
            }
        }
        XYChart chart = QuickChart.getChart("The average amount of pixels per interval", "Pixel number",
                "Average value", "y(x)", xDataNew, yDataNew);
        new SwingWrapper(chart).displayChart("The average amount of pixels per interval");
    }

    /**
     * @param pixels        array of pixels
     * @param intervalStart start point of the check interval
     * @param intervalEnd   end point of the check interval
     * @param interval      interval
     * @param dXOut         pixel number
     * @param dYOut         average sum of pixels (sum of pixels / number)
     */
    private static void arrayOfAverageValue(int[] pixels,
                                            int intervalStart,
                                            int intervalEnd,
                                            int interval,
                                            double[] dXOut,
                                            double[] dYOut) {
        int tempX = 0;
        int sum = 0;
        for (int i = intervalStart, ii = 0, j = 0; i < intervalEnd; i++, ii++) {
            sum += pixels[i];
            // Если остаток от деления ii на interval == 0 
            // или i - № последнего пикселя
            if (((ii % interval == 0) | (i == (intervalEnd - 1))) & ii > 0) {
                dYOut[j] = sum / interval;
                tempX += interval;
                dXOut[j] = tempX;
                sum = 0;
                j++;
            }
        }
    }

    public static void showEntropyCalculationChart(int[] pixels,
                                                   int intervalStart,
                                                   int intervalEnd,
                                                   int interval) {

        double[] xData = new double[(pixels.length / interval) + 2];
        double[] yData = new double[(pixels.length / interval) + 2];
        double[] xDataNew = new double[xData.length - 2];
        double[] yDataNew = new double[yData.length - 2];

        entropy(pixels, intervalStart, intervalEnd, interval, xData, yData);

        for (int i = 0; i < xDataNew.length; i++) {
            xDataNew[i] = xData[i]; ///xDataNew.length
            yDataNew[i] = yData[i];
            if (xDataNew[i] == 0 & yDataNew[i] == 0) {
                xDataNew[i] = xDataNew[i - 1];
                yDataNew[i] = yDataNew[i - 1];
            }
            if (xDataNew[i] == 0) {
                xDataNew[i] = xDataNew[i - 1];
            }
        }
        XYChart chart = QuickChart
                .getChart("Pixel entropy", "Pixel number", "Entropy", "y(x)",
                        xDataNew, yDataNew);
        new SwingWrapper(chart).displayChart("Pixel entropy");
    }

    /**
     * @param pixels        array of pixels
     * @param intervalStart start point of the check interval
     * @param intervalEnd   end point of the check interval
     * @param interval      interval
     * @param dXOut         pixel number
     * @param dYOut         pixel entropy per interval
     */
    private static void entropy(int[] pixels,
                                int intervalStart,
                                int intervalEnd,
                                int interval,
                                double[] dXOut,
                                double[] dYOut) {

        double sumPix1 = 0;
        double sumPix0 = 0;
        double sumBit1 = 0;
        double sumBit0 = 0;
        double tempX = 0;
        double p1, p0, h;

        int[] extMsg = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            extMsg[i] = Steganography.extractIntMsgFromPixel(pixels[i]);
        }
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

            if ((ii % interval == 0) | (i == intervalEnd - 2)) {
                tempX += interval;
                dXOut[k] = tempX;
                p1 = sumPix1 / (sumPix1 + sumPix0);
                p0 = (sumPix0 / (sumPix1 + sumPix0));

                // log a (b) = log c (b) / log c (a)
                h = -((p0 * (Math.log(p0) / Math.log(2))) + (p1 * (Math.log(p1) / Math.log(2))));
                dYOut[k] = h;
                sumPix1 = 0;
                sumBit1 = 0;
                sumPix0 = 0;
                sumBit0 = 0;
                k++;
            }
            ii++;
        }
    }
}
