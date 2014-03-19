package runtime.graphics.frame;

import runtime.Runtime;
import runtime.graphics.GraphicsState;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
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
        double scale = GraphicsState.psUnitToPixel(1);
        //return new double[]{scale * 1., 0., 0., scale * (-1.), 0, GraphicsState.psUnitToPixel(frame.psHeight) - 1};
        //return new double[]{1., 0., 0., -1., 0, frame.getHeight()};
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
            //PSPoint jBegin = javaMatrix.iTransform(psBegin.getX(), psBegin.getY());
            //PSPoint jEnd = javaMatrix.iTransform(psEnd.getX(), psEnd.getY());
            //g.draw(new Line2D.Double((int) jBegin.getX(), (int) jBegin.getY(), (int) jEnd.getX(), (int) jEnd.getY()));
            g.draw(new Line2D.Double((int) psBegin.getX(), (int) psBegin.getY(), (int) psEnd.getX(), (int) psEnd.getY()));
            /*g.scale(2, 1);
            g.translate(200,200);
            g.rotate(Math.PI / 4);
            g.drawString("gi",30, 30);*/
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
            PSPoint absCenter = arc.getCenter();
            int xR = (int) arc.getXRadius();
            int yR = (int) arc.getYRadius();
            int x = (int) (absCenter.getX() - xR);
            int y = (int) (absCenter.getY() - yR);
            int width = 2 * xR;
            int height = 2 * yR;
            int angle = (int) arc.getAngle();
            g.drawArc(x, y, width, height, (int) -arc.getAngleFirst(), -angle);
        }
    }

    public class PSFrame extends JFrame {
        //private JLabel label = new JLabel("asd");
        //public int psHeight = 631;
        public int psHeight = 800;
        //public int psWidth = 445;
        public int psWidth = 600;

        private PSFrame() {
            super();
            //setSize((int) GraphicsState.psUnitToPixel(psWidth),(int) GraphicsState.psUnitToPixel(psHeight));
            setSize(psWidth, psHeight);
            setLocation(0, 0);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //setResizable(false);
            //getContentPane().setLayout(new BorderLayout());
            //getContentPane().add(label);
            //label.setVisible(true);
            /*addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    //label.setText("x:" + e.getX() + ",y: " + e.getY());
                    //repaint();
                }
            });*/
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            AffineTransform saveAT = g2.getTransform();
            g2.setTransform(new AffineTransform(getJavaTransformMatrix()));
            drawCurrentPath(state.currentPath, g2);
            drawClippingPath(state.clippingPath, g2);
            g2.setTransform(saveAT);

        }

    }
}
