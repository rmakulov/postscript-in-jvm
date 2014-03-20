package runtime.graphics.paths;

/**
 * Created by user on 14.03.14.
 */

import runtime.graphics.GraphicsSettings;
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

    public Arc(PSPoint absBegin, PSPoint absEnd, PSPoint absCenter, double absXRadius, double absYRadius,
               double relAngle1, double relAngle2, boolean clockwise, GraphicsSettings settings) {
        super(absBegin, absEnd, settings);
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

    double lowerLeftX(double angle1, double angle2, PSPoint start, PSPoint finish) {
        double angle = 180.;
        angle += 360 * (int) (angle1 / 360);
        while (angle < angle1) {
            angle += 360;
        }
        if (angle > angle2) {
            return Math.min(start.getX(), finish.getX());
        } else {
            return center.getX() - xRadius;
        }
    }

    double lowerLeftY(double angle1, double angle2, PSPoint start, PSPoint finish) {
        double angle = 270.;
        angle += 360 * (int) (angle1 / 360);
        while (angle < angle1) {
            angle += 360;
        }
        if (angle > angle2) {
            return Math.min(start.getY(), finish.getY());
        } else {
            return center.getY() - yRadius;
        }
    }

    double upperRightX(double angle1, double angle2, PSPoint start, PSPoint finish) {
        double angle = 0.;
        angle += 360 * (int) (angle1 / 360);
        while (angle < angle1) {
            angle += 360;
        }
        if (angle > angle2) {
            return Math.max(start.getX(), finish.getX());
        } else {
            return center.getX() + xRadius;
        }
    }

    double upperRightY(double angle1, double angle2, PSPoint start, PSPoint finish) {
        double angle = 90;
        angle += 360 * (int) (angle1 / 360);
        while (angle < angle1) {
            angle += 360;
        }
        if (angle > angle2) {
            return Math.max(start.getY(), end.getY());
        } else {
            return center.getY() + yRadius;
        }
    }


    public BoundingBox getBBox() {
        if (Math.abs(angleSecond - angleFirst) > 360) {
            PSPoint leftLowerPoint = new PSPoint(center.getX() - xRadius, center.getY() - xRadius);
            PSPoint rightUpperPoint = new PSPoint(center.getX() + xRadius, center.getY() + xRadius);
            return new BoundingBox(leftLowerPoint, rightUpperPoint);
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
        double angle1 = angleFirst, angle2 = angleSecond;
        PSPoint start = new PSPoint(center.getX() + xRadius * Math.cos(angle1 * Math.PI / 180.),
                center.getY() + yRadius * Math.sin(angle1 * Math.PI / 180.));
        PSPoint finish = new PSPoint(center.getX() + xRadius * Math.cos(angle2 * Math.PI / 180.),
                center.getY() + xRadius * Math.sin(angle2 * Math.PI / 180.));

        if (angle1 < 0) {
            int k = (int) (1 + -angle1 / 360.);
            angle1 += k * 360;
            angle2 += k * 360;
        }

        double llx = lowerLeftX(angle1, angle2, start, finish);
        double lly = lowerLeftY(angle1, angle2, start, finish);
        double urx = upperRightX(angle1, angle2, start, finish);
        double ury = upperRightY(angle1, angle2, start, finish);

        return new BoundingBox(llx, lly, urx, ury);
    }

    public boolean isAnyCommonPointWithBoundingBox(BoundingBox box) {
        if (!box.isIntersect(getBBox())) return false;
        double ax = box.leftX / xRadius;
        double bx = box.rightX / xRadius;
        double ay = box.lowerY / yRadius;
        double by = box.rightX / yRadius;
        if (ax > 1 || bx < -1 || ay > 1 || by < -1) return false;
        //SectionSet set=
        //todo
        return false;
    }

    public boolean isOnArc(double angle) { // supposed point is
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
        if (angle1 < 0) {
            int k = (int) (1 + -angle1 / 360.);
            angle1 += k * 360;
            angle2 += k * 360;
        }
        angle += 360 * ((int) (angle1 / 360) - (int) (angle / 360));
        while (angle < angle1) {
            angle += 360;
        }
        return angle < angle2;
    }

    public boolean isInCircle(PSPoint p) {
        double difx = (p.getX() - center.getX()), dify = (p.getY() - center.getY());
        return (difx * difx) / (xRadius * xRadius) + (dify * dify) * (yRadius * yRadius) <= 1;
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
        return new Arc(begin, end, new PSPoint(center.getX(), center.getY()), xRadius,
                yRadius, angleFirst, angleSecond, clockwise, graphicsSettings.clone());
    }


    @Override
    public int rayIntersect(PSPoint p) {
        double[] line = PSPoint.lineEquation(p, new PSPoint(p.getX() + 1, p.getY() + 1));
        double dist = center.distanceToLine(line);
        if (dist < xRadius) {
            double[] ellipse = new double[]{center.getX(), center.getY(), xRadius, yRadius};
            PSPoint[] iPoints = PSPoint.intersectEllipseLine(ellipse, line);
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
