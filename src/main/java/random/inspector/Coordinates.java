package random.inspector;

public class Coordinates {

    private double[] x;
    private double[] y;

    public Coordinates(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public static Coordinates validateCoordinates(Coordinates coordinates) {
        double[] xData = coordinates.getX();
        double[] yData = coordinates.getY();
        double[] xDataNew = new double[xData.length - 2];
        double[] yDataNew = new double[yData.length - 2];
        for (int i = 0; i < xDataNew.length; i++) {
            xDataNew[i] = xData[i];
            yDataNew[i] = yData[i];
            if (xDataNew[i] == 0 && yDataNew[i] == 0) {
                xDataNew[i] = xDataNew[i - 1];
                yDataNew[i] = yDataNew[i - 1];
            }
            if (xDataNew[i] == 0) {
                xDataNew[i] = xDataNew[i - 1];
            }
        }
        return new Coordinates(xDataNew, yDataNew);
    }
}
