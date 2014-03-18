package runtime.graphics.paths;

import java.awt.*;

import runtime.graphics.point.PSPoint;

/**
 * Created by user on 14.03.14.
 */
public abstract class PathSection implements Cloneable {
    private PSPoint begin = new PSPoint();
    private PSPoint end = new PSPoint();

    public abstract PSPoint[] getBBox();

    public abstract PathSection clone();

    public abstract PathSection draw(Graphics g) ;

    public abstract int rayIntersect(PSPoint p) ;

    //public abstract void addPathSection() ;

    public PathSection() {

    }

    public PathSection(PSPoint beginPoint, PSPoint endPoint) {
        begin = beginPoint;
        end = endPoint;
    }

    public PathSection(double x, double y, double r, double angle1, double angle2) {
        begin = new PSPoint(x + r * Math.cos(angle1 *Math.PI/180), y + r * Math.sin(angle1*Math.PI/180));
        end   = new PSPoint(x + r * Math.cos(angle2 *Math.PI/180), y + r * Math.sin(angle2*Math.PI/180));
    }

    public PSPoint getBegin() {
        return begin;
    }

    public PSPoint getEnd() {
        return end;
    }
}
