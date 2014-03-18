package runtime.graphics.frame;

import runtime.Runtime;
import runtime.graphics.GraphicsState;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by Дмитрий on 18.03.14.
 */
public class PSDrawer {
    private static PSDrawer instance = new PSDrawer();
    private PSFrame frame = new PSFrame();
    private GraphicsState state = GraphicsState.getInstance();
    private runtime.Runtime runtime = Runtime.getInstance();

    private PSDrawer() {
    }

    public static PSDrawer getInstance() {
        if (instance == null) {
            instance = new PSDrawer();
        }
        return instance;
    }

    public void showPage() {
        frame.setVisible(true);
        frame.repaint();
    }

    private void drawClippingPath(PSPath clippingPath, Graphics2D g2) {
        //todo
    }

    private void drawCurrentPath(PSPath path, Graphics2D g2) {
        ArrayList<SequentialPath> sequentialPaths = path.getSequentialPaths();
        for (SequentialPath p : sequentialPaths) {
            drawSequentialPath(p, g2);
        }

    }

    private void drawSequentialPath(SequentialPath sequentialPath, Graphics2D g2) {
        switch (sequentialPath.getPaintingState()) {
            case FILL:
                fillSequentialPath(sequentialPath, g2);
                break;
            case STROKE:
                strokeSequentialPath(sequentialPath, g2);
                break;
            default:
        }
    }

    private void fillSequentialPath(SequentialPath sequentialPath, Graphics2D g2) {

    }

    private void strokeSequentialPath(SequentialPath sequentialPath, Graphics2D g2) {
        ArrayList<PathSection> pathSections = sequentialPath.getPathSections();
        for (PathSection section : pathSections) {
            PathSectionDrawer sectionDrawer;
            if (section instanceof LineSegment) {
                sectionDrawer = new LineSegmentDrawer((LineSegment) section, g2);
            } else {
                sectionDrawer = new ArcDrawer((Arc) section, g2);
            }
            sectionDrawer.draw();
        }
    }

    //translate on h in y and mirror scale
    private double[] getJavaTransformMatrix() {
        return new double[]{1., 0., 0., -1., 0, frame.getHeight()};
    }

    private abstract class PathSectionDrawer {
        protected TransformMatrix javaMatrix = new TransformMatrix(getJavaTransformMatrix());
        Graphics2D g;

        protected PathSectionDrawer(Graphics2D g) {
            this.g = g;
        }

        public abstract void draw();

        protected void setGraphicsSettings() {
            g.setColor(state.color);
            g.setStroke(new BasicStroke((float) state.getLineWidthInPixels(),
                    state.lineCap,
                    state.lineJoin,
                    (float) state.miterLimit, state.dash, state.dashPhase));

        }
    }

    private class LineSegmentDrawer extends PathSectionDrawer {
        LineSegment segment;

        public LineSegmentDrawer(LineSegment segment, Graphics2D g2) {
            super(g2);
            this.segment = segment;
        }

        @Override
        public void draw() {
            PSPoint psBegin = segment.getBegin();
            PSPoint psEnd = segment.getEnd();
            PSPoint jBegin = javaMatrix.iTransform(psBegin.getX(), psBegin.getY());
            PSPoint jEnd = javaMatrix.iTransform(psEnd.getX(), psEnd.getY());
            g.draw(new Line2D.Double((int) jBegin.getX(), (int) jBegin.getY(), (int) jEnd.getX(), (int) jEnd.getY()));
            frame.repaint();
        }
    }

    private class ArcDrawer extends PathSectionDrawer {
        Arc arc;

        private ArcDrawer(Arc arc, Graphics2D g2) {
            super(g2);
            this.arc = arc;
        }

        @Override
        public void draw() {

        }
    }

    public class PSFrame extends JFrame {
        private PSFrame() {
            super();
            setSize(445, 631);
            setLocation(0, 0);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            drawCurrentPath(state.currentPath, g2);
            drawClippingPath(state.clippingPath, g2);
        }
    }
}
