package runtime.graphics.paths;

/**
 * Created by user on 14.03.14.
 */

import runtime.graphics.figures.PSPoint;

public class Arc extends PathSection {
    private PSPoint center;
    private double xRadius;
    private double yRadius;
    private double angleFirst;
    private double angleSecond;
    private boolean clockwise;
    //private TransformMatrix transformMatrix;


/*    public Arc(PSPoint point, double r, double angle1, double angle2, boolean clwise, TransformMatrix transformMatrix) {//angle1 & angle2 in degrees
        super(point.getX(), point.getY(), r, angle1, angle2);
        center = point;
        xRadius = r;
        while (angle2 < angle1) {
            angle2 += 360;
        }
        angleFirst = angle1; //degreeToRadian(angle1) ;
        angleSecond = angle2; //degreeToRadian(angle2) ;
        clockwise = clwise;
        this.transformMatrix = transformMatrix;
    }*/

/*    public Arc(PSPoint absBegin, PSPoint absEnd,
               PSPoint relCenter, double relRadius, double relAngle1, double relAngle2, boolean clockwise,
               TransformMatrix transformMatrix) {
        super(absBegin, absEnd);
        center = relCenter;
        xRadius = relRadius;
        angleFirst = relAngle1;
        angleSecond = relAngle2;
        this.clockwise = clockwise;
        this.transformMatrix = transformMatrix;
    }*/

    public Arc(PSPoint absBegin, PSPoint absEnd, PSPoint absCenter, double absXRadius, double absYRadius, double relAngle1, double relAngle2, boolean clockwise) {
        super(absBegin, absEnd);
        center = absCenter;
        xRadius = absXRadius;
        yRadius = absYRadius;
        angleFirst = relAngle1;
        angleSecond = relAngle2;
        this.clockwise = clockwise;
    }

    public void setClockwise(boolean clwise) {
        clockwise = clwise;
    }

    public double getAngleFirst() {
        return angleFirst;
    }

    public void setAngleFirst(double angle) {
        angleFirst = angle;
    }

    public double getAngle() {
        return (angleSecond - angleFirst) * (clockwise ? -1 : 1);
    }

    public double getAngleSecond() {
        return angleSecond;
    }

    public void setAngleSecond(double angle) {
        angleSecond = angle;
    }

    double lowerLeftX(double angle1, double angle2, PSPoint beg, PSPoint end) {
        double k = 180., llx;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            llx = Math.min(beg.getX(), end.getX());
        } else {
            llx = center.getX() - xRadius;
        }
        return llx;
    }

    double lowerLeftY(double angle1, double angle2, PSPoint beg, PSPoint end) {
        double k = 270., lly;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            lly = Math.min(beg.getY(), end.getY());
        } else {
            lly = center.getY() - xRadius;
        }
        return lly;
    }

    double upperRightX(double angle1, double angle2, PSPoint beg, PSPoint end) {
        double k = 0., urx;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            urx = Math.max(beg.getX(), end.getX());
        } else {
            urx = center.getX() + xRadius;
        }
        return urx;
    }

    double upperRightY(double angle1, double angle2, PSPoint beg, PSPoint end) {
        double k = 90, ury;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            ury = Math.max(beg.getY(), end.getY());
        } else {
            ury = center.getY() + xRadius;
        }
        return ury;
    }

    //todo redo it
    public BoundingBox getBBox() {
        return new BoundingBox(0, 0, 0, 0);
        /*double angle1 = angleFirst, angle2 = angleSecond;
        if (Math.abs(angleSecond - angleFirst) > 360) {
            PSPoint[] box = new PSPoint[2];
            box[0] = new PSPoint(center.getX() - xRadius, center.getY() - xRadius);
            box[1] = new PSPoint(center.getX() + xRadius, center.getY() + xRadius);
            //return box;
            return null;
        }
        if (clockwise) {
            Arc newArc = (Arc) clone();
            newArc.setClockwise(false);
            double angF = newArc.angleFirst;
            double angS = newArc.angleSecond;
            newArc.setAngleFirst(angS);
            newArc.setAngleSecond(angF + 360);
            return newArc.getBBox();
        }
        PSPoint beg = new PSPoint(center.getX() + xRadius * Math.cos(angle1 * Math.PI / 180.),
                center.getY() + xRadius * Math.sin(angle1 * Math.PI / 180.));
        PSPoint end = new PSPoint(center.getX() + xRadius * Math.cos(angle2 * Math.PI / 180.),
                center.getY() + xRadius * Math.sin(angle2 * Math.PI / 180.));
        while (angle1 < 0) {
            angle1 += 360;
            angle2 += 360;
        }
        double llx = lowerLeftX(angle1, angle2, beg, end);
        double lly = lowerLeftY(angle1, angle2, beg, end);
        double urx = upperRightX(angle1, angle2, beg, end);
        double ury = upperRightY(angle1, angle2, beg, end);

        PSPoint[] box = new PSPoint[2];
        box[0] = new PSPoint(llx, lly);
        box[1] = new PSPoint(urx, ury);
        //return box;
        return null;*/
    }

    public boolean isOnArc(double angle) {
        double angle1 = angleFirst;
        double angle2 = angleSecond;
        if (Math.abs(angle1 - angle2) > 360) {
            return true;
        }
        if (clockwise) {
            Arc newArc = (Arc) clone();
            newArc.setClockwise(false);
            newArc.setAngleFirst(angle2);
            newArc.setAngleSecond(angle1 + 360);
            return newArc.isOnArc(angle);
        }
        while (angle < angle1) { // maybe it will be too long
            angle += 360;
        }
        return angle < angle2;
    }

    public boolean isInCircle(PSPoint p) {
        //todo change all methods
        //PSPoint relP = transformMatrix.transform(p.getX(), p.getY());
        //double difx = (relP.getX() - center.getX()), dify = (relP.getY() - center.getY());
        //return difx * difx + dify * dify <= xRadius * xRadius;
        return false;
    }

    public boolean isGoingThroughInterior(PSPoint start, PSPoint end) {
        double t = 0.5;
        PSPoint p = PSPoint.pointByParameter(start, end, t);
        while (!isInCircle(p) && t >= 1. / 33554432) {
            t /= 2.;
            p = PSPoint.pointByParameter(start, end, t);
        }
        return isInCircle(p);
    }

    @Override
    public PathSection clone() {
        return new Arc(begin, end, new PSPoint(center.getX(), center.getY()), xRadius, yRadius, angleFirst, angleSecond, clockwise);
    }

/*    @Override
    public void draw(Graphics g) {
         //todo
         //g.drawArc(g) ;
    }*/

    @Override
    public int rayIntersect(PSPoint p) {
        double[] line = PSPoint.lineEquation(p, new PSPoint(p.getX() + 1, p.getY() + 1));
        double dist = center.distanceToLine(line);
        if (dist < xRadius) {
            double[] circle = new double[]{center.getX(), center.getY(), xRadius};
            PSPoint[] iPoints = PSPoint.intersectCircleLine(circle, line);
            double angle0 = Math.atan2(iPoints[0].getY() - center.getY(), iPoints[0].getX() - center.getX());
            double angle1 = Math.atan2(iPoints[1].getY() - center.getY(), iPoints[1].getX() - center.getX());
            angle0 = radianToDegree(angle0);
            angle1 = radianToDegree(angle1);
            if (isOnArc(angle0) && isOnArc(angle1)) {
                return 0;
            }
            if (isOnArc(angle0)) {
                if (isGoingThroughInterior(p, iPoints[0])) {
                    return clockwise ? 1 : -1;
                } else {
                    return clockwise ? -1 : 1;
                }
            } else if (isOnArc(angle1)) {
                if (isGoingThroughInterior(p, iPoints[1])) {
                    return clockwise ? 1 : -1;
                } else {
                    return clockwise ? -1 : 1;
                }
            }
        }
        return 0;
    }

    public static double degreeToRadian(double degrees) {
        return degrees * Math.PI / 180;
    }

    public static double radianToDegree(double radians) {
        return radians * 180 / Math.PI;
    }

    /*public TransformMatrix getTransformMatrix() {
        return transformMatrix;
    }
*/
    public PSPoint getCenter() {
        return center;
    }

    public double getXRadius() {
        return xRadius;
    }

    public double getYRadius() {
        return yRadius;
    }

    public boolean isClockwise() {
        return clockwise;
    }
}
