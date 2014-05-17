package runtime.graphics.frame;

import runtime.graphics.GState;
import runtime.graphics.GraphicsSettings;
import runtime.graphics.paths.PSPath;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

/**
 * Created by Дмитрий on 18.03.14.
 */
public class PSDrawer {
    private static PSDrawer instance = new PSDrawer();
    private PSFrame frame = PSFrame.getInstance();
    protected GState gState = GState.getInstance();
    private long lastDrawTime = 0;
    private final long repaintTime = 50;

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

    public void fill() {
        Graphics2D g2 = (Graphics2D) PSImage.getGraphics();
        PSPath path = gState.currentPath;
        setGraphicsSettings(g2, gState.graphicsSettings);
        GeneralPath generalPath = path.getGeneralPath();
        generalPath.setWindingRule(Path2D.WIND_NON_ZERO);
        g2.clip(gState.clippingPath.getGeneralPath());
        g2.fill(generalPath);
        gState.newCurrentPath();
        repaintImage();
    }

    private void repaintImage() {
        long time = System.currentTimeMillis();
        if (Math.abs(time - lastDrawTime) > repaintTime) {
            frame.repaint();
            lastDrawTime = time;
        }

    }

    public void eofill() {
        Graphics2D g2 = (Graphics2D) PSImage.getGraphics();
        PSPath path = gState.currentPath;
        setGraphicsSettings(g2, gState.graphicsSettings);
        GeneralPath generalPath = path.getGeneralPath();
        generalPath.setWindingRule(Path2D.WIND_EVEN_ODD);
        g2.clip(gState.clippingPath.getGeneralPath());
        g2.fill(generalPath);
        gState.newCurrentPath();
        repaintImage();
    }

    public void stroke() {
        Graphics2D g2 = (Graphics2D) PSImage.getGraphics();
        PSPath path = gState.currentPath;
        setGraphicsSettings(g2, gState.graphicsSettings);
        g2.clip(gState.clippingPath.getGeneralPath());
        g2.draw(path.getGeneralPath());
        gState.newCurrentPath();
        repaintImage();
    }

    public void clip() {
        Graphics2D g2 = (Graphics2D) PSImage.getGraphics();
        PSPath path = gState.currentPath;
        g2.clip(gState.clippingPath.getGeneralPath());
        g2.clip(path.getGeneralPath());
        Shape clip = g2.getClip();
        gState.clippingPath = new PSPath(new GeneralPath(clip));
        gState.newCurrentPath();
        repaintImage();
    }

    public void setGraphicsSettings(Graphics2D g, GraphicsSettings settings) {
        if (settings == null) return;
        g.setColor(settings.color);
        g.setStroke(new BasicStroke((float) gState.getLineWidthInPixels(),
                settings.lineCap,
                settings.lineJoin,
                (float) settings.miterLimit,
                //settings.getDash(), settings.dashPhase));
                settings.dash, settings.dashPhase));

    }

/*    public class PSFrame extends JFrame {
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

           *//* if (isPainted) {
                super.paint(g);
                return;
            }
            isPainted = true;*//*

            Graphics2D g2 = (Graphics2D) g;

            g2.setTransform(new AffineTransform(getJavaTransformMatrix()));
            for (DrawPath path : gState.drawPaths) {
                drawCurrentPath(path, g2);
            }
            // gState.drawPaths.clear() ;


        }
    }*/

}
