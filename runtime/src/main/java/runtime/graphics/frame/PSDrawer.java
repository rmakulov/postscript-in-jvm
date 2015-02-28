package runtime.graphics.frame;

import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.PSName;
import psObjects.values.simple.PSNull;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Runtime;
import runtime.graphics.GState;
import runtime.graphics.GraphicsSettings;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.PSPath;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by Дмитрий on 18.03.14.
 */
public class PSDrawer {
    private static PSDrawer instance = new PSDrawer();
    private PSFrame frame = PSFrame.getInstance();
    private Runtime runtime = Runtime.getInstance();
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
        PSPath path = runtime.getGState().currentPath;
        setGraphicsSettings(g2, runtime.getGState().graphicsSettings);
        GeneralPath generalPath = path.getGeneralPath();
        generalPath.setWindingRule(Path2D.WIND_NON_ZERO);
        g2.clip(runtime.getGState().clippingPath.getGeneralPath());
        g2.fill(generalPath);
        runtime.getGState().newCurrentPath();
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
        PSPath path = runtime.getGState().currentPath;
        setGraphicsSettings(g2, runtime.getGState().graphicsSettings);
        GeneralPath generalPath = path.getGeneralPath();
        generalPath.setWindingRule(Path2D.WIND_EVEN_ODD);
        g2.clip(runtime.getGState().clippingPath.getGeneralPath());
        g2.fill(generalPath);
        runtime.getGState().newCurrentPath();
        repaintImage();
    }

    public void stroke() {
        Graphics2D g2 = (Graphics2D) PSImage.getGraphics();
        GState gState = runtime.getGState();
        PSPath path = gState.currentPath;
        setGraphicsSettings(g2, gState.graphicsSettings);
        g2.clip(gState.clippingPath.getGeneralPath());
        g2.draw(path.getGeneralPath());
        gState.newCurrentPath();
        repaintImage();
    }

    public void clip() {
        Graphics2D g2 = (Graphics2D) PSImage.getGraphics();
        PSPath path = runtime.getGState().currentPath;
        g2.clip(runtime.getGState().clippingPath.getGeneralPath());
        g2.clip(path.getGeneralPath());
        Shape clip = g2.getClip();
        runtime.getGState().clippingPath = new PSPath(new GeneralPath(clip));
        runtime.getGState().newCurrentPath();
        repaintImage();
    }

    public void setGraphicsSettings(Graphics2D g, GraphicsSettings settings) {
        if (settings == null) return;
        g.setColor(settings.color);
        g.setStroke(new BasicStroke((float) runtime.getGState().getLineWidthInPixels(),
                settings.lineCap,
                settings.lineJoin,
                (float) settings.miterLimit,
                //settings.getDash(), settings.dashPhase));
                settings.dash, settings.dashPhase));

    }

    public static void reset() {
        instance = new PSDrawer();
    }

    /*
    * getGraphics => psY = y
    * getDefaultGraphics => psY = 843 - y
    * */

    public void show(String str) {
        Graphics2D g2 = (Graphics2D) PSImage.getDefaultGraphics();
        GState gState = runtime.getGState();
        PSDictionary fontDictionary = (PSDictionary) gState.getFont().getValue();
        String nameFont = ((PSName) fontDictionary.get(new PSObject(new PSName("name"))).getValue()).getStrValue();
        int scaleFont = ((PSInteger) fontDictionary.get(new PSObject(new PSName("scale"))).getValue()).getIntValue();
        PSObject matrix = fontDictionary.get(new PSObject(new PSName("matrix")));
        Font font = new Font(nameFont, Font.PLAIN, scaleFont);
        if (!matrix.getValue().equals(PSNull.NULL)) {
            AffineTransform trans = new TransformMatrix(matrix).toAffineTransform();
            font = new Font(nameFont, Font.PLAIN, 40);
            font.deriveFont(trans);
        }
        g2.setFont(font);
        int psX = (int) gState.currentPoint.getX();
        int psY = (int) gState.currentPoint.getY();
        setGraphicsSettings(g2, gState.graphicsSettings);
        double[] arr = gState.cTM.getDoubleArray();
        double translateY = PSImage.height - psY;
        double translateX = psX;
        AffineTransform affineTransform = new AffineTransform(arr[0], -arr[1], -arr[2], arr[3], translateX, translateY);
        g2.setTransform(affineTransform);
//        g2.clip(runtime.getGState().clippingPath.getGeneralPath());
        g2.drawString(str, 0, 0);

        Rectangle2D rect = font.getStringBounds(str, g2.getFontRenderContext());
        double w = rect.getWidth();
        PSPoint point = gState.cTM.transform(w, 0);
        PSPoint point2 = gState.cTM.transform(0, 0);
        double shiftX = point.getX() - point2.getX();
        double shiftY = point.getY() - point2.getY();
        double endPsX = psX + shiftX;
        double endPsY = psY + shiftY;
        gState.currentPoint = new PSPoint(endPsX, endPsY);
        gState.currentPath.getGeneralPath().moveTo(endPsX, endPsY);
        repaintImage();
    }
}

