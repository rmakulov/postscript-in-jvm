package runtime.graphics.paths;

import runtime.graphics.figures.PSPoint;

import java.awt.*;

/**
 * Created by user on 14.03.14.
 */
public class LineSegment extends PathSection {
    public LineSegment(PSPoint pBegin, PSPoint pEnd) {
        super(pBegin, pEnd);
    }

    public PSPoint[] getBBox() {
        PSPoint beg = getBegin();
        PSPoint end = getEnd();
        double llx = Math.min(beg.getX(), end.getX()), lly = Math.min(beg.getY(), end.getY());
        double urx = Math.max(beg.getX(), end.getX()), ury = Math.max(beg.getY(), end.getY());
        PSPoint[] box = new PSPoint[2];
        box[0] = new PSPoint(llx, lly);
        box[1] = new PSPoint(urx, ury);
        return box;
    }

    @Override
    public PathSection clone() {
        PSPoint beg = getBegin();
        PSPoint end = getEnd();
        return new LineSegment(new PSPoint(beg.getX(), beg.getY()),
                new PSPoint(end.getX(), end.getY()));
    }

    @Override
    public void draw(Graphics g) {
        int x = (int) getBegin().getX(), y = (int) getBegin().getY()  ;
        int width = (int)(getBegin().getX() - getEnd().getX()) ;
        int height = (int)(getBegin().getY() - getEnd().getY()) ;
        g.drawLine(x, y,width, height) ;
    }

    @Override
    public int rayIntersect(PSPoint p) { // return +1 if left-> right, else -1 else 0
        PSPoint lineBegin = getBegin();
        PSPoint lineEnd = getEnd();
        double[] line1 = PSPoint.lineEquation(lineBegin, lineEnd);
        double[] line2 = PSPoint.lineEquation(p, new PSPoint(p.getX() + 1, p.getY() + 1));
        PSPoint pIntersect = PSPoint.lineIntersect(line1, line2);
        if (pIntersect.isOnSection(lineBegin, lineEnd)) {
            double angle = Math.atan2(lineEnd.getY() - pIntersect.getY(), lineEnd.getX() - pIntersect.getX())
                    - Math.atan2(pIntersect.getY() - p.getY(), pIntersect.getX() - p.getX());
            if ((angle >= 0 && angle <= Math.PI) || (angle >= -2 * Math.PI && angle <= Math.PI)) {//=> from left to right
                return 1;
            } else {// Pi.. 2Pi union 0 ..-Pi    => from right tp left
                return -1;
            }
        }
        return 0;
    }


}
