package runtime.graphics.paths;

/**
 * Created by user on 14.03.14.
 */

import runtime.graphics.point.PSPoint;

public class Arc extends PathSection {
    private PSPoint center;
    private double radius;
    private double angleFirst;
    private double angleSecond;
    private boolean clockwise;

    public Arc() {
    }

    public Arc(PSPoint point, double r, double angle1, double angle2, boolean clwise) {//angle1 & angle2 in degrees
        super(point.getX(), point.getY(), r, angle1, angle2);
        center = point;
        radius = r;
        while (angle2 < angle1) {
            angle2 += 360;
        }
        angleFirst = angle1; //degreeToRadian(angle1) ;
        angleSecond = angle2; //degreeToRadian(angle2) ;
        clockwise = clwise;
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

    public double getAngleSecond() {
        return angleSecond;
    }

    public void setAngleSecond(double angle) {
        angleSecond = angle;
    }


    public PSPoint[] getBBox() {
        double angle1 = angleFirst, angle2 = angleSecond;
        if (Math.abs(angleSecond - angleFirst) > 360) {
            PSPoint[] box = new PSPoint[2];
            box[0] = new PSPoint(center.getX() - radius, center.getY() - radius);
            box[1] = new PSPoint(center.getX() + radius, center.getY() + radius);
            return box;
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
        PSPoint beg = new PSPoint(center.getX() + radius * Math.cos(angle1 * Math.PI / 180.),
                center.getY() + radius * Math.sin(angle1 * Math.PI / 180.));
        PSPoint end = new PSPoint(center.getX() + radius * Math.cos(angle2 * Math.PI / 180.),
                center.getY() + radius * Math.sin(angle2 * Math.PI / 180.));
        while (angle1 < 0) {
            angle1 += 360;
            angle2 += 360;
        }
        double llx, lly, urx, ury ;
        // ---- minimum y counting---------------------//
        double k = 270.;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            lly = Math.min(beg.getY(), end.getY());
        } else {
            lly = center.getY() - radius;
        }
        // ---- minimum y counting---------------------//
        //------maximum y counting---------------------//
        k = 90.;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            ury = Math.max(beg.getY(), end.getY());
        } else {
            ury = center.getY() + radius;
        }
        // ---- maximum y counting---------------------//
        //------minimum x counting---------------------//
        k = 180.;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            llx = Math.min(beg.getX(), end.getX());
        } else {
            llx = center.getX() - radius;
        }
        // ---- minimum x counting---------------------//
        //------maximum x counting---------------------//
        k = 0.;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            urx = Math.max(beg.getX(), end.getX());
        } else {
            urx = center.getX() + radius;
        }
        // ---- maximum x counting---------------------//
        PSPoint[] box = new PSPoint[2];
        box[0] = new PSPoint(llx, lly);
        box[1] = new PSPoint(urx, ury);
        return box;
    }

    @Override
    public PathSection clone() {
        return new Arc(new PSPoint(center.getX(), center.getY()), radius, angleFirst, angleSecond, clockwise);
    }

    @Override
    public int rayIntersect(PSPoint p) {
        return 0;
    }

    public static double degreeToRadian(double degrees) {
        return degrees * Math.PI / 180;
    }
}