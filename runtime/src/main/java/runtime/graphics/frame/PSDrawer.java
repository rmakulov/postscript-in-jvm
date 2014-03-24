package runtime.graphics.frame;

import runtime.graphics.GraphicsSettings;
import runtime.graphics.GraphicsState;
import runtime.graphics.paths.DrawPath;
import runtime.graphics.paths.PSPath;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

/**
 * Created by Дмитрий on 18.03.14.
 */
public class PSDrawer {
    private static PSDrawer instance = new PSDrawer();
    private PSFrame frame = new PSFrame();
    //private boolean isPainted = false;
    protected GraphicsState gState = GraphicsState.getInstance();

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
        //frame.paint(frame.getGraphics());
        //isPainted = true;
    }

    private void drawClippingPath(PSPath clippingPath, Graphics2D g2) {
        if (clippingPath == null) {
            return;
        }
        //todo if clipping path is not closed
        GeneralPath generalPath = clippingPath.getLastSequentialPath();
        //g2.clip(generalPath);
    }

    private void drawCurrentPath(DrawPath drawPath, Graphics2D g2) {
        if (drawPath == null || drawPath.path == null) return;

        if (drawPath.paintingState == DrawPath.PaintingState.FILL) {
            for (GeneralPath generalPath : drawPath.path.getGeneralPaths()) {
                g2.setColor(drawPath.graphicsSettings.color);
                g2.fill(generalPath);
            }
        } else if (drawPath.paintingState == DrawPath.PaintingState.STROKE) {
            for (GeneralPath generalPath : drawPath.path.getGeneralPaths()) {
                Stroke s = g2.getStroke();
                Shape s1 = s.createStrokedShape(generalPath);
                g2.draw(s1);
            }
        }
    }


    //translate on h in y and mirror scale
    private double[] getJavaTransformMatrix() {
        double scale = 1;
        return new double[]{scale * 1., 0., 0., scale * (-1.), 0, frame.getHeight()};
    }

    public void setGraphicsSettings(Graphics2D g, GraphicsSettings settings) {
        if (settings == null) return;
        g.setColor(settings.color);
        g.setStroke(new BasicStroke((float) gState.getLineWidthInPixels(),
                settings.lineCap,
                settings.lineJoin,
                (float) settings.miterLimit));
        //, settings.dash, settings.dashPhase));

    }

    public class PSFrame extends JFrame {
        public int psHeight = 631 * 4 / 3;
        //public int psHeight = 800;
        public int psWidth = 445 * 4 / 3;
        //public int psWidth = 600;


        private PSFrame() {
            super();
            setSize(psWidth, psHeight);
            setLocation(0, 0);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setBackground(Color.WHITE);
        }

        @Override
        public void paint(Graphics g) {

           /* if (isPainted) {
                super.paint(g);
                return;
            }
            isPainted = true;*/

            Graphics2D g2 = (Graphics2D) g;

            g2.setTransform(new AffineTransform(getJavaTransformMatrix()));
            for (DrawPath path : gState.drawPaths) {
                drawCurrentPath(path, g2);
            }
            // gState.drawPaths.clear() ;


        }
    }

}
