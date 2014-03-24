package runtime.graphics.unuse;

import runtime.graphics.GraphicsSettings;
import runtime.graphics.figures.PSPoint;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by user on 14.03.14.
 */
public class SequentialPath {
    private GraphicsSettings graphicsSettings;

    public void setGraphicsSettings(GraphicsSettings graphicsSettings) {
        this.graphicsSettings = graphicsSettings;
    }

    public GraphicsSettings getGraphicsSettings() {
        return graphicsSettings;
    }

    public enum PaintingState {
        FILL, NONE, STROKE
    }

    private ArrayList<PathSection> pathSections = new ArrayList<PathSection>();
    private PaintingState paintingState = PaintingState.NONE;
    private BoundingBox boundingBox ;
    private boolean isClosed = false;

    public SequentialPath() {
    }

    public SequentialPath(PathSection firstPath) {
        addPathSection(firstPath);
    }

/*    public SequentialPath(PaintingState paintingState, ArrayList<PathSection> pathSections) {
        this.paintingState = paintingState;
        this.pathSections = pathSections;
    }*/

    public void addPathSection(PathSection ps) {
        isClosed = true;
        if (pathSections.isEmpty()) {
            boundingBox = ps.getBBox() ;
        } else {
            boundingBox = boundingBox.expand(ps.getBBox());
        }
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

    public BoundingBox getBBox() {
        return boundingBox ;
    }

    public void close() {
        isClosed = true;
        PSPoint pBegin = pathSections.get(0).getBegin();
        PSPoint pEnd = pathSections.get(pathSections.size() - 1).getEnd();
        if (Point.distance(pBegin.getX(), pBegin.getY(), pEnd.getX(), pEnd.getY()) > 0.00001) {
            pathSections.add(new LineSegment(pBegin, pEnd, graphicsSettings));
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

    public int intersect(int x, int y) {
        return intersect(new PSPoint(x, y));
    }

    public boolean isClosed() {
        return isClosed;
    }
}
