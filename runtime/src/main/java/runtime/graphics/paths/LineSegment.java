package runtime.graphics.paths;

import runtime.graphics.point.PSPoint ;

/**
 * Created by user on 14.03.14.
 */
public class LineSegment extends PathSection {
    public LineSegment(PSPoint pBegin, PSPoint pEnd){
        super(pBegin, pEnd) ;
    }
    public PSPoint[] getBBox(){
        PSPoint beg = getBegin() ;
        PSPoint end = getEnd() ;
        double llx = Math.min(beg.getX(),end.getX()), lly = Math.min(beg.getY(), end.getY()) ;
        double urx = Math.max(beg.getX(),end.getX()), ury = Math.max(beg.getY(), end.getY()) ;
        PSPoint[] box = new PSPoint[2] ;
        box[0] = new PSPoint(llx, lly) ;
        box[1] = new PSPoint(urx, ury) ;
        return box ;
    }

    @Override
    public PathSection clone() {
        PSPoint beg = getBegin() ;
        PSPoint end = getEnd() ;
        return new LineSegment(new PSPoint(beg.getX(),beg.getY()),
                               new PSPoint(end.getX(),end.getY()) );
    }

    @Override
    public int rayIntersect(PSPoint p) {
        return 0;
    }


}
