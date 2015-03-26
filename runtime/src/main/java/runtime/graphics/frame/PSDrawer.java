package runtime.graphics.frame;

import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.PSName;
import psObjects.values.simple.PSNull;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;
import runtime.Runtime;
import runtime.graphics.GState;
import runtime.graphics.GraphicsSettings;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.PSPath;

import java.awt.*;
import java.awt.font.GlyphVector;
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

    public void fill(Context context) {
        Graphics2D g2 = (Graphics2D) PSImage.getGraphics();
        PSPath path = context.getGState().currentPath;
        setGraphicsSettings(context, g2, context.getGState().graphicsSettings);
        GeneralPath generalPath = path.getGeneralPath();
        generalPath.setWindingRule(Path2D.WIND_NON_ZERO);
        g2.clip(context.getGState().clippingPath.getGeneralPath());
        g2.fill(generalPath);
        context.getGState().newCurrentPath();
        repaintImage();
    }

    private void repaintImage() {
        long time = System.currentTimeMillis();
        if (Math.abs(time - lastDrawTime) > repaintTime) {
            frame.repaint();
            lastDrawTime = time;
        }

    }

    public void eofill(Context context) {
        Graphics2D g2 = (Graphics2D) PSImage.getGraphics();
        PSPath path = context.getGState().currentPath;
        setGraphicsSettings(context, g2, context.getGState().graphicsSettings);
        GeneralPath generalPath = path.getGeneralPath();
        generalPath.setWindingRule(Path2D.WIND_EVEN_ODD);
        g2.clip(context.getGState().clippingPath.getGeneralPath());
        g2.fill(generalPath);
        context.getGState().newCurrentPath();
        repaintImage();
    }

    public void clip(Context context) {
        Graphics2D g2 = (Graphics2D) PSImage.getGraphics();
        PSPath path = context.getGState().currentPath;
        g2.clip(context.getGState().clippingPath.getGeneralPath());
        g2.clip(path.getGeneralPath());
        Shape clip = g2.getClip();
        context.getGState().clippingPath = new PSPath(new GeneralPath(clip));
        context.getGState().newCurrentPath();
        repaintImage();
    }

    public void setGraphicsSettings(Context context, Graphics2D g, GraphicsSettings settings) {
        if (settings == null) return;
        g.setColor(settings.color);
        g.setStroke(new BasicStroke((float) context.getGState().getLineWidthInPixels(),
                settings.lineCap,
                settings.lineJoin,
                (float) settings.miterLimit,
                //settings.getDash(), settings.dashPhase));
                settings.dash, settings.dashPhase));

    }

    public static void reset() {
        instance = new PSDrawer();
    }


    public static Shape generateShapeFromText(Graphics2D g2, Font font, String string, float x, float y) {

        try {
            GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(), string);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape shape = gv.getOutline(x, y);
            return shape;
        } catch (Exception e) {
            return null;
        }
    }

    public void stroke(Context context) {
        Graphics2D g2 = (Graphics2D) PSImage.getGraphics();
        GState gState = context.getGState();
        PSPath path = gState.currentPath;
        setGraphicsSettings(context, g2, gState.graphicsSettings);
        g2.clip(gState.clippingPath.getGeneralPath());
        g2.draw(path.getGeneralPath());
        gState.newCurrentPath();
        repaintImage();
    }

    /*
    * getGraphics => psY = y
    * getDefaultGraphics => psY = 843 - y
    * */

    public void show(Context context, String str) {
        Graphics2D g2 = (Graphics2D) PSImage.getDefaultGraphics();
        GState gState = context.getGState();
        Font font = getFont(context);
        g2.setFont(font);
        setGraphicsSettings(context, g2, gState.graphicsSettings);
        try {
            g2.setTransform(new AffineTransform(PSImage.getJavaTransformMatrix()));
            g2.clip(gState.clippingPath.getGeneralPath());
            g2.setTransform(new AffineTransform(PSImage.getJavaTransformMatrix()).createInverse());
        } catch (Exception e) {
        }

        int psX = (int) gState.currentPoint.getX();
        int psY = (int) gState.currentPoint.getY();


        double[] arr = gState.cTM.getDoubleArray();
        double translateY = PSImage.height - psY;
        double translateX = psX;

        AffineTransform affineTransform = new AffineTransform(arr[0], -arr[1], -arr[2], arr[3], translateX, translateY);
        g2.setTransform(affineTransform);

        Shape s = generateShapeFromText(g2, font, str, 0, 0);
        g2.draw(s);
        g2.fill(s);

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

    public void showOld(Context context, String str) {
        Graphics2D g2 = (Graphics2D) PSImage.getDefaultGraphics();
        GState gState = context.getGState();

        Font font = getFont(context);
        g2.setFont(font);
        int psX = (int) gState.currentPoint.getX();
        int psY = (int) gState.currentPoint.getY();

        setGraphicsSettings(context, g2, gState.graphicsSettings);

        double[] arr = gState.cTM.getDoubleArray();
        double translateY = PSImage.height - psY;
        double translateX = psX;

        AffineTransform affineTransform = new AffineTransform(arr[0], -arr[1], -arr[2], arr[3], translateX, translateY);
        g2.setTransform(affineTransform);

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

    public Font getFont(Context context) {
        GState gState = context.getGState();
        PSDictionary fontDictionary = (PSDictionary) gState.getFont().getValue();
//        System.out.println(fontDictionary);
        String nameFont = ((PSName) fontDictionary.get(new PSObject(new PSName("name"))).getValue()).getStrValue();
        int scaleFont = ((PSInteger) fontDictionary.get(new PSObject(new PSName("scale"))).getValue()).getIntValue();
        PSObject matrix = fontDictionary.get(new PSObject(new PSName("matrix")));
        Font font = new Font(nameFont, Font.PLAIN, scaleFont);
        if (!matrix.getValue().equals(PSNull.NULL)) {
            AffineTransform trans = new TransformMatrix(matrix).toAffineTransform();
            font = new Font(nameFont, Font.PLAIN, 40);
            font.deriveFont(trans);
        }
        return font;
    }
}

