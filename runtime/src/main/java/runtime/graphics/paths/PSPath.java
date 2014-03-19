package runtime.graphics.paths;

/**
 * Created by user on 15.03.14.
 */

import runtime.graphics.GraphicsSettings;
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

    public BoundingBox getBBox() {
        if (sequentialPaths == null) {
            return null;
        }
        BoundingBox box = null;
        for (SequentialPath sP : sequentialPaths) {
            if (box == null) {
                box = sP.getBBox();
            } else {
                box.expand(getBBox()) ;
            }
        }
        return box ;
    }

    public void setPaintingStateOfLastSequentialPath(SequentialPath.PaintingState paintingState, GraphicsSettings settings) {
        SequentialPath sequentialPath = sequentialPaths.get(sequentialPaths.size() - 1);
        sequentialPath.setPaintingState(paintingState);
        sequentialPath.setGraphicsSettings(settings);
    }

    public void addSequentialPath(SequentialPath path) {
        sequentialPaths.add(path);
    }

    //absolute coordinates in postscript
    public void addLineSegment(PSPoint begining, PSPoint end, GraphicsSettings settings) {
        if (sequentialPaths.size() == 0) {
            SequentialPath newSPath = new SequentialPath(new LineSegment(begining, end, settings));
            sequentialPaths.add(newSPath);
        } else {
            getLastSP().addPathSection(new LineSegment(begining, end, settings));
        }
    }

    //absolute coordinates in postscript
    public void addArc(PSPoint absBegin, PSPoint absEnd, PSPoint absCenter, double absXRadius, double absYRadius,
                       double relAngle1, double RelAngle2, boolean clockwise, GraphicsSettings settings) {
        Arc arc = new Arc(absBegin, absEnd, absCenter, absXRadius, absYRadius, relAngle1, RelAngle2, clockwise, settings);
        PSPoint curPoint = getLastSP().getEnd();
        if (sequentialPaths.size() == 0) {
            if (curPoint == null) {
                SequentialPath newSPath = new SequentialPath(arc);
                sequentialPaths.add(newSPath);
            } else {
                SequentialPath newSPath = new SequentialPath(new LineSegment(curPoint, absBegin, settings));
                newSPath.addPathSection(arc);
                sequentialPaths.add(newSPath);
            }
        } else if (PSPoint.distance(curPoint, absBegin) > 0.0001) {
            getLastSP().addPathSection(new LineSegment(curPoint, absBegin, settings));
            getLastSP().addPathSection(arc);
        } else {
            getLastSP().addPathSection(arc);
        }
    }

    public void closePath() {
        for (SequentialPath spath : sequentialPaths) {
            spath.close();
        }
    }


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
