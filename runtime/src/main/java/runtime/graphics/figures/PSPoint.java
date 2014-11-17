package runtime.graphics.figures;

import java.awt.geom.Point2D;

/**
 * Created by user on 14.03.14.
 */
public class PSPoint extends Point2D {

    private double xPos;
    private double yPos;


    public PSPoint() {
    }

    public PSPoint(double pX, double pY) {
        xPos = pX;
        yPos = pY;
    }

    public PSPoint clone() {
        return new PSPoint(xPos, yPos);
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    @Override
    public void setLocation(double x, double y) {
        xPos = x;
        yPos = y;
    }

    @Override
    public String toString() {
        return "PSPoint{" +
                "x=" + xPos +
                ", y=" + yPos +
                '}';
    }
}
