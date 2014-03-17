package runtime.graphics.paths;

/**
 * Created by user on 14.03.14.
 */

import runtime.graphics.point.PSPoint;

import java.awt.*;

public class Arc extends PathSection {
    private PSPoint center;
    private double  radius;
    private double  angleFirst;
    private double  angleSecond;
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

    double lowerLeftX(double angle1, double angle2, PSPoint beg, PSPoint end){
        double k = 180., llx;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            llx = Math.min(beg.getX(), end.getX());
        } else {
            llx = center.getX() - radius;
        }
        return llx ;
    }

    double lowerLeftY(double angle1, double angle2,PSPoint beg,PSPoint end){
        double k = 270., lly;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            lly = Math.min(beg.getY(), end.getY());
        } else {
            lly = center.getY() - radius;
        }
        return lly ;
    }

    double upperRightX(double angle1,double angle2,PSPoint beg,PSPoint end){
        double k = 0., urx ;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            urx = Math.max(beg.getX(), end.getX());
        } else {
            urx = center.getX() + radius;
        }
        return urx ;
    }

    double upperRightY(double angle1,double  angle2,PSPoint beg,PSPoint end) {
        double k = 90, ury ;
        while (k < angle1) {
            k += 360;
        }
        if (k > angle2) {
            ury = Math.max(beg.getY(), end.getY());
        } else {
            ury = center.getY() + radius;
        }
        return ury ;
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
        double llx = lowerLeftX(angle1,  angle2, beg, end) ;
        double lly = lowerLeftY(angle1,  angle2, beg, end) ;
        double urx = upperRightX(angle1, angle2, beg, end) ;
        double ury = upperRightY(angle1, angle2, beg, end) ;

        PSPoint[] box = new PSPoint[2];
        box[0] = new PSPoint(llx, lly);
        box[1] = new PSPoint(urx, ury);
        return box;
    }

    public boolean isOnArc(double angle){
        double angle1 = angleFirst ;
        double angle2 = angleSecond ;
        if(Math.abs(angle1 - angle2) > 360){
            return true ;
        }
        if(clockwise){
            Arc newArc = (Arc) clone() ;
            newArc.setClockwise(false) ;
            newArc.setAngleFirst(angle2) ;
            newArc.setAngleSecond(angle1 + 360) ;
            return newArc.isOnArc( angle ) ;
        }
        while(angle < angle1 ){ // maybe it will be too long
            angle += 360 ;
        }
        return angle < angle2 ;
    }

    public boolean isInCircle(PSPoint p){
        double difx =  (p.getX() - center.getX()), dify = (p.getY() - center.getY());
        return difx*difx + dify*dify <= radius * radius ;
    }

    public boolean isGoingThroughInterior(PSPoint start, PSPoint end){
        double t = 0.5 ;
        PSPoint p = PSPoint.pointByParameter(start, end, t) ;
        while(!isInCircle(p) && t >= 1./33554432){
            t /= 2. ;
            p = PSPoint.pointByParameter(start, end, t) ;
        }
        return isInCircle(p);
    }

    @Override
    public PathSection clone() {
        return new Arc(new PSPoint(center.getX(), center.getY()), radius, angleFirst, angleSecond, clockwise);
    }

    @Override
    public PathSection draw(Graphics g) {
        return null;
    }

    @Override
    public int rayIntersect(PSPoint p) {
        double[] line = PSPoint.lineEquation( p, new PSPoint(p.getX() + 1, p.getY() + 1) ) ;
        double dist = center.distanceToLine(line) ;
        if(dist < radius){
            double [] circle = new double[]{center.getX(), center.getY(), radius } ;
            PSPoint[] iPoints = PSPoint.intersectCircleLine( circle, line ) ;
            double angle0 = Math.atan2(iPoints[0].getY() - center.getY(), iPoints[0].getX() - center.getX()) ;
            double angle1 = Math.atan2(iPoints[1].getY() - center.getY(), iPoints[1].getX() - center.getX() ) ;
            angle0 = radianToDegree(angle0) ;
            angle1 = radianToDegree(angle1) ;
            if(isOnArc(angle0) && isOnArc(angle1)){
                return 0 ;
            }
            if(isOnArc(angle0)){
                if( isGoingThroughInterior(p, iPoints[0]) ){
                    return clockwise ? 1 : -1 ;
                }
                else{
                    return clockwise ? -1 : 1 ;
                }
            }
            else if(isOnArc(angle1)){
                if( isGoingThroughInterior(p,iPoints[1]) ){
                    return clockwise ? 1 : -1 ;
                }
                else{
                    return clockwise ? -1 : 1 ;
                }
            }
        }
        return 0;
    }

    public static double degreeToRadian(double degrees) {
        return degrees * Math.PI / 180 ;
    }
     public static double radianToDegree(double radians) {
        return radians * 180 / Math.PI ;
    }
}
