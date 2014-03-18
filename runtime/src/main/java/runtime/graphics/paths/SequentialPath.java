package runtime.graphics.paths;

import runtime.graphics.figures.PSPoint;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by user on 14.03.14.
 */
public class SequentialPath {
    public enum PaintingState {
        FILL, NONE, STROKE
    }

    private ArrayList<PathSection> pathSections = new ArrayList<PathSection>();
    private PaintingState paintingState = PaintingState.NONE;

    public SequentialPath() {

    }

    public SequentialPath(PathSection firstPath) {
        pathSections.add(firstPath);
    }

    public SequentialPath(PaintingState paintingState, ArrayList<PathSection> pathSections) {
        this.paintingState = paintingState;
        this.pathSections = pathSections;
    }

    public void addPathSection(PathSection ps) {
        pathSections.add(ps);
    }

    public void setPaintingState(PaintingState paintingState) {
        this.paintingState = paintingState;
    }

    public boolean isFilled() {
        return paintingState == PaintingState.FILL;
    }

    public PaintingState getPaintingState() {
        return paintingState;
    }

    public ArrayList<PathSection> getPathSections() {
        return pathSections;
    }

    public PSPoint getBegining() {
        if (pathSections.size() == 0) {
            return null;
        }
        return pathSections.get(0).getBegin();
    }

    public PSPoint getEnd() {
        if (pathSections.size() == 0) {
            return null;
        }
        return pathSections.get(pathSections.size() - 1).getEnd();
    }

    public PSPoint[] getBBox() {
        if (pathSections == null) {
            return null;
        }
        PSPoint[] box = null;
        for (PathSection pSection : pathSections) {
            PSPoint[] newBox = pSection.getBBox();
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

    public void close() {
        PSPoint pBegin = pathSections.get(0).getBegin();
        PSPoint pEnd = pathSections.get(pathSections.size() - 1).getEnd();
        if (Point.distance(pBegin.getX(), pBegin.getY(), pEnd.getX(), pEnd.getY()) > 0.00001) {
            pathSections.add(new LineSegment(pBegin, pEnd));
        }
    }

    public SequentialPath clone() {
        SequentialPath newPath = new SequentialPath();
        for (PathSection ps : pathSections) {
            addPathSection(ps.clone());
        }
        return newPath;
    }


    public int intersect(PSPoint point) {
        int ans = 0;
        for (PathSection ps : pathSections) {
            ans += ps.rayIntersect(point);
        }
        return ans;
    }
}
