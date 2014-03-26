package runtime.graphics;

import runtime.graphics.figures.PSPoint;
import runtime.graphics.frame.PSImage;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.PSPath;
import runtime.graphics.save.GSave;

import java.awt.*;

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
        initClip();
        graphicsSettings = GraphicsSettings.mainInstance;
    }

    public static GraphicsState getInstance() {
        return instance;
    }

    public GSave getSnapshot(boolean isMadeByGSaveOp) {
        return new GSave(currentPath.clone(), clippingPath.clone(), cTM.clone(), cloneGraphicsSettings(), isMadeByGSaveOp, currentPoint.clone());
    }

    public void setSnapshot(GSave gSave) {
        currentPath = gSave.getCurrentPath().clone();
        clippingPath = gSave.getClippingPath().clone();
        cTM = gSave.getcTM().clone();
        graphicsSettings = gSave.getSettings().clone();
        currentPoint = gSave.getCurrentPoint().clone();
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

    public void initClip() {
        clippingPath = new PSPath();
        clippingPath.getGeneralPath().append(new Rectangle(0, 0, PSImage.width, PSImage.height), true);
    }
}
