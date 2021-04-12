package random.inspector;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import random.stego.Steganography;

import javax.swing.*;

public class MovingAverageInspector {

    private MovingAverageInspector() {
    }

    public static void showMovingAverageChart(int[] pixels, int intervalStart, int intervalEnd, int interval) {

        Coordinates coordinates = arrayOfAverageValue(Steganography.formLastBitsPixelArray(pixels),
                intervalStart, intervalEnd, interval);
        coordinates = Coordinates.validateCoordinates(coordinates);

        XYChart chart = QuickChart.getChart("The average amount of pixels per interval", "Pixel number",
                "Average value", "y(x)", coordinates.getX(), coordinates.getY());
        JFrame chartFrame = new SwingWrapper<>(chart).setTitle("The average amount of pixels per interval")
                .displayChart();
        SwingUtilities.invokeLater(() -> chartFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE));
    }

    /**
     * @param pixels        array of pixels
     * @param intervalStart start point of the check interval
     * @param intervalEnd   end point of the check interval
     * @param interval      interval
     */
    private static Coordinates arrayOfAverageValue(int[] pixels,
                                                   int intervalStart,
                                                   int intervalEnd,
                                                   int interval) {

        double[] xData = new double[(pixels.length / interval) + 2];
        double[] yData = new double[(pixels.length / interval) + 2];

        int tempX = 0;
        int sum = 0;
        for (int i = intervalStart, ii = 0, j = 0; i < intervalEnd; i++, ii++) {
            sum += pixels[i];
            // if remainder of the division ii / interval == 0  or i is last pixel number
            if (((ii % interval == 0) || (i == (intervalEnd - 1))) && ii > 0) {
                yData[j] = sum / interval;
                tempX += interval;
                xData[j] = tempX;
                sum = 0;
                j++;
            }
        }
        return new Coordinates(xData, yData);
    }
}
