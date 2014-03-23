package runtime.graphics;

import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.DrawPath;
import runtime.graphics.paths.PSPath;

import java.util.ArrayList;

/**
 * Created by use 1r on 14.03.14.
 */
public class GraphicsState {
    private static GraphicsState instance = new GraphicsState();
    public PSPoint currentPoint;
    public PSPath currentPath;
    public PSPath clippingPath;
    public TransformMatrix cTM;
    public GraphicsSettings graphicsSettings;
    public ArrayList<DrawPath> drawPaths;
/*    public Color color;
    public double lineWidth; // 1/72 inch
    public int lineJoin;
    public int lineCap;
    public double miterLimit;
    public float[] dash = new float[]{};
    public float dashPhase = 0;*/

    private GraphicsState() {
        currentPath = new PSPath();
        drawPaths = new ArrayList<DrawPath>();
        currentPoint = new PSPoint();
        cTM = new TransformMatrix();
        clippingPath = new PSPath(); //todo page size rectangle
        graphicsSettings = GraphicsSettings.mainInstance;
    }

    public static GraphicsState getInstance() {
        return instance;
    }


    //---------------------Fonts
    public static double psUnitToPixel(double psUnits) {
        return psUnits / 72 * 96;
        //96 - это число пикселей в дюйме
    }

    public static double pixelToPSUnit(double pixels) {
        return pixels / 96 * 72;
    }

    public double getLineWidthInPixels() {
        return psUnitToPixel(graphicsSettings.lineWidth);
    }

    public GraphicsSettings cloneGraphicsSettings() {
        return graphicsSettings.clone();
    }

    public void addCurrentPathInDrawPath(DrawPath.PaintingState state) {
        drawPaths.add(new DrawPath(currentPath, state, graphicsSettings.clone()));
    }
}
