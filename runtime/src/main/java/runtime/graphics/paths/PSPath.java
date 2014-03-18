package runtime.graphics.paths;

/**
 * Created by user on 15.03.14.
 */

import runtime.graphics.figures.PSPoint;

import java.util.ArrayList;

public class PSPath {
    private ArrayList<SequentialPath> sequentialPaths;

    public PSPath() {
        sequentialPaths = new ArrayList<SequentialPath>();
    }

    public SequentialPath getLastSP() {
        if (sequentialPaths.size() == 0) {
            return null;
        }
        return sequentialPaths.get(sequentialPaths.size() - 1);
    }

    public PSPoint[] getBBox() {
        if (sequentialPaths == null) {
            return null;
        }
        PSPoint[] box = null;
        for (SequentialPath sP : sequentialPaths) {
            PSPoint[] newBox = sP.getBBox();
            if (box == null) {
                box = newBox;
            } else {
                if (newBox[0].getX() < box[0].getX()) box[0].setX(newBox[0].getX());
                if (newBox[0].getY() < box[0].getY()) box[0].setY(newBox[0].getY());
                if (newBox[1].getX() < box[1].getX()) box[1].setX(newBox[1].getX());
                if (newBox[1].getY() < box[1].getY()) box[1].setY(newBox[1].getY());
            }
        }
        return box;
    }

    public void setPaintingStateOfLastSequentialPath(SequentialPath.PaintingState paintingState) {
        sequentialPaths.get(sequentialPaths.size() - 1).setPaintingState(paintingState);
    }

    public void addSequentialPath(SequentialPath path) {
        sequentialPaths.add(path);
    }

    //absolute coordinates in postscript
    public void addLineSegment(PSPoint begining, PSPoint end) {
        if (sequentialPaths.size() == 0 || PSPoint.distance(getLastSP().getEnd(), begining) > 0.0001) {
            SequentialPath newSPath = new SequentialPath(new LineSegment(begining, end));
            sequentialPaths.add(newSPath);
            return;
        }
        getLastSP().addPathSection(new LineSegment(begining, end));
    }

    public void addArc(PSPoint currentPoint, PSPoint center, double radius, double angle1, double angle2, boolean clockwise) {
        Arc arc = new Arc(center, radius, angle1, angle2, clockwise);
        if (currentPoint == null) {
            if (sequentialPaths.size() == 0 || PSPoint.distance(sequentialPaths.get(sequentialPaths.size() - 1).getEnd(), arc.getBegin()) > 0.001) {//distance - maybe it's undue
                SequentialPath newSPath = new SequentialPath(arc);              //because if Path != null then currentPoint != null
                return;
            }
            getLastSP().addPathSection(arc);
            ;
        } else {
            addLineSegment(currentPoint, arc.getBegin());
            getLastSP().addPathSection(arc);
            ;
        }
    }

    public void closePath() {
        for (SequentialPath spath : sequentialPaths) {
            spath.close();
        }
    }


/*    public void draw(Graphics g) {

        for (SequentialPath path : sequentialPaths) {
            for (PathSection ps : path.getPathSections()) {
                ps.draw(g);
            }
        }
    }*/

    public PSPath clone() {
        PSPath newPath = new PSPath();
        for (SequentialPath spath : sequentialPaths) {
            newPath.addSequentialPath(spath.clone());
        }
        return newPath;
    }

    public boolean isInside(PSPoint point) {
        int count = 0;
        for (SequentialPath sp : sequentialPaths) {
            count += sp.intersect(point);
        }
        return count != 0; //nonzero-winding-number-rule
    }

    public ArrayList<SequentialPath> getSequentialPaths() {
        return sequentialPaths;
    }
}
