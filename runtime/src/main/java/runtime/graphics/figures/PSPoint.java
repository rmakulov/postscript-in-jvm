package runtime.graphics.figures;

/**
 * Created by user on 14.03.14.
 */
public class PSPoint {

    private double x;
    private double y;


    public PSPoint() {
    }

    public PSPoint(double pX, double pY) {
        x = pX;
        y = pY;
    }

    public PSPoint clone() {
        return new PSPoint(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "PSPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
