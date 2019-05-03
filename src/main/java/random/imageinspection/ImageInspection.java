package random.imageinspection;

import random.stego.Steganography;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class ImageInspection {

    public static void showMovingAverageChart(int[] pix, int boundAvg1, int boundAvg2, int intrvAvg) {
        double[] xData = new double[(pix.length / intrvAvg) + 2];
        double[] yData = new double[(pix.length / intrvAvg) + 2];
        double[] xDataNew = new double[xData.length - 2];
        double[] yDataNew = new double[yData.length - 2];

        arrayOfAverageValue(Steganography.extractLastBits(pix), boundAvg1, boundAvg2, intrvAvg, xData, yData);

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
        // Create Chart
        XYChart chart = QuickChart.getChart("The average amount of pixels per interval", "Pixel number",
                "Average value", "y(x)", xDataNew, yDataNew);
        // Show it
        SwingWrapper swingWrapper = new SwingWrapper(chart);//.displayChart();  
        swingWrapper.displayChart("The average amount of pixels per interval");
        //new SwingWrapper(chart).displayChart();
    }

    // Входные параметры: массив пикселей, начальная и конечная точка итенвала проверки, 
    // интервал, через который сторятся точки и 2 массива чисел с плавающей точкой. В которые записываются:
    // по dX - Pixel number, по dY - среднеяя сумма пикселей (сумма пикселей на их количество)
    private static void arrayOfAverageValue(int[] pixels, int bound1, int bound2, int interval, double[] dX, double[] dY) {
        int tempX = 0;
        int sum = 0;
        for (int i = bound1, ii = 0, j = 0; i < bound2; i++, ii++) {
            sum += pixels[i];
            // Если остаток от деления ii на interval == 0 
            // или i - № последнего пикселя
            if (((ii % interval == 0) | (i == (bound2 - 1))) & ii > 0) {
                dY[j] = sum / interval;
                tempX += interval;
                dX[j] = tempX;
                sum = 0;
                j++;
            }
        }
    }

    public static void showEntropyCalculationChart(int[] pix, int boundEntropy1, int boundEntropy2, int intrvEntropy) {

        double[] xData = new double[(pix.length / intrvEntropy) + 2];
        double[] yData = new double[(pix.length / intrvEntropy) + 2];
        double[] xDataNew = new double[xData.length - 2];
        double[] yDataNew = new double[yData.length - 2];

        entropy(pix, boundEntropy1, boundEntropy2, intrvEntropy, xData, yData);

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
        // Create Chart
        XYChart chart = QuickChart
                .getChart("Pixel entropy", "Pixel number", "Entropy", "y(x)",
                        xDataNew, yDataNew);
        // Show it
        new SwingWrapper(chart).displayChart("Pixel entropy");
    }

    /**
     * @param pixels   массив пикселей
     * @param bound1   начальная точка итенвала проверки
     * @param bound2   конечная точка итенвала проверки
     * @param interval интервал, через который сторятся точки
     * @param dX       Pixel number
     * @param dY       энтропия пикселей за интервал
     */
    private static void entropy(int[] pixels, int bound1, int bound2, int interval, double[] dX, double[] dY) {

        double sumPix1 = 0;
        double sumPix0 = 0;
        double sumBit1 = 0;
        double sumBit0 = 0;
        double tempX = 0;
        double p1, p0, h;

        int[] pixs = pixels;
        int[] extMsg = new int[pixs.length];
        for (int i = 0; i < pixs.length; i++) {
            extMsg[i] = Steganography.extFromPix(pixs[i]);
        }
        for (int i = bound1, ii = 0, k = 0; i < bound2; i++) {
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

            if ((ii % interval == 0) | (i == bound2 - 2)) {
                tempX += interval;
                dX[k] = tempX;
                p1 = sumPix1 / (sumPix1 + sumPix0);
                p0 = (sumPix0 / (sumPix1 + sumPix0));

                // log a (b) = log c (b) / log c (a)
                h = -((p0 * (Math.log(p0) / Math.log(2))) + (p1 * (Math.log(p1) / Math.log(2))));
                dY[k] = h;
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
