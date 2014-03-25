package runtime.graphics;

import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.PSPath;

public class GraphicsState {
    private static GraphicsState instance = new GraphicsState();
    public PSPoint currentPoint;
    public PSPath currentPath;
    public PSPath clippingPath;
    public TransformMatrix cTM;
    public GraphicsSettings graphicsSettings;

    private GraphicsState() {
        currentPath = new PSPath();
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

    public void newCurrentPath() {
        currentPath = new PSPath();
        currentPoint = null;
    }

}
