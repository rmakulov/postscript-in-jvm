package runtime.graphics.figures;

import java.awt.geom.Point2D;

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

    public static double distance(PSPoint a, PSPoint b) {
        double xDif = a.getX() - b.getX();
        double yDif = a.getY() - b.getY();
        return Math.sqrt(xDif * xDif + yDif * yDif);
    }

    public boolean isInBox(PSPoint[] box) {
        if (x >= box[0].getX() && x <= box[1].getX() && y >= box[1].getY() && y <= box[1].getY()) {
            return true;
        }
        return false;
    }

    public Point2D getPoint2D() {
        return new Point2D.Double(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double newX) {
        x = newX;
    }

    public void setY(double newY) {
        y = newY;
    }

    public static double[] lineEquation(PSPoint a, PSPoint b) {
        double x1 = a.getX(), y1 = a.getY();
        double x2 = b.getX(), y2 = b.getY();
        return new double[]{y1 - y2, x2 - x1, y1 * x2 + x1 * y2};
    }

    public static PSPoint getVector(PSPoint begin, PSPoint end) {
        return new PSPoint(end.getX() - begin.getX(), end.getY() - begin.getY());
    }

    public double distanceToLine(double[] line) {
        double a = line[0], b = line[1], c = line[2];
        return Math.abs((a * x + b * y + c) / (a * a + b * b));
    }

    public static PSPoint lineIntersect(double[] line1, double[] line2) {
        double a1 = line1[0], b1 = line1[1], c1 = line1[2];
        double a2 = line2[0], b2 = line2[1], c2 = line2[2];
        double x = (c1 * b2 - c2 * b1) / (b1 * a2 - b2 * a1), y = (c2 * a1 - c1 * a2) / (b1 * a2 - b2 * a1);
        return new PSPoint(x, y);
    }

    public static double[] squareEquation(double a, double b, double c) {
        double d = b * b - 4 * a * c;
        return new double[]{(-b + Math.sqrt(d)) / (2 * a), (-b - Math.sqrt(d)) / (2 * a)};
    }


    public static PSPoint[] intersectEllipseLine(double[] ellipse, double[] line) {
        double x1 = ellipse[0], y1 = ellipse[1], rx = ellipse[2], ry = ellipse[3]; //(x/rx)^2 + (y/ry)^2 = 1 && ax + by + c = 0
        double a = line[0], b = line[1], c = line[2];                              // y = -(c+ax)/b
        //  ((rx*a/b)^2 +(ry)^2)*x^2 + (2a*c*(rx)^2/(b)^2)*x + ((rx*c/b)^2 -(ry*rx)^2) = 0 = L*x^2 + M*x + N
        //   after replacement x = x - x1
        //   we have L*x^2 + (M - 2*L*x1)*x + (L*(x1)^2 - M*x1 + N) = 0
        double L = rx * rx * a * a / (b * b) + ry * ry, M = 2 * rx * rx * a * c / (b * b), N = rx * rx * c * c / (b * b) - ry * ry * rx * rx;
        double[] x = squareEquation(L, M - 2 * L * x1, L * x1 * x1 - M * x1 + N);
        //afetr replacement y = y-y1
        double[] y = {y1 - (c + a * x[0]) / b, y1 - (c + a * x[1]) / b};
        return new PSPoint[]{new PSPoint(x[0], y[0]), new PSPoint(x[1], y[1])};
    }
    //correct
    /*public static PSPoint[] intersectCircleLine(double[] circle, double[] line) {
        PSPoint[] iPoints = new PSPoint[2];
        double x1 = circle[0], y1 = circle[0], r = circle[2];  // (x-x1)^2 + (y-y1)^2 = r^2
        double a = line[0], b = line[1], c = line[2];         // Ax + By + C = 0
        double F = -2 * c * a / (b * b) + 2 * y1 * a / b;          // y = (c-a*x)/b
        double newC = c * c / (b * b) - 2 * y1 * c / b + y1 * y1; //(y-y1)^2 = a^2/b^2*x^2 + F*x + newC
        double[] x = squareEquation(1 + a * a / (b * b), F - 2 * x1, newC - r * r + x1 * x1);
        double[] y = new double[]{(c - a * x[0]) / b, (c - a * x[1]) / b};
        iPoints[0] = new PSPoint(x[0], y[0]);
        iPoints[1] = new PSPoint(x[1], y[1]);
        return iPoints;
    }*/

    public static PSPoint pointByParameter(PSPoint start, PSPoint end, double t) { // 0 <= t <= 1
        return new PSPoint(start.getX() * (1 - t) + t * end.getX(),
                start.getY() * (1 - t) + t * end.getY());
    }

    public boolean isOnSection(PSPoint sBegin, PSPoint sEnd) {
        //supposed point is on line of section
        PSPoint vector = getVector(sBegin, sEnd); //vectorx, vectory
        double t;
        if (vector.getX() != 0) {
            t = (x - sBegin.getX()) / vector.getX();
        } else {
            t = (y - sBegin.getY()) / vector.getY();
        }
        return (t >= 0 && t <= 1);
    }

    @Override
    public String toString() {
        return "PSPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
