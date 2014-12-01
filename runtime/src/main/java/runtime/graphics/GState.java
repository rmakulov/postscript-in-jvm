package runtime.graphics;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.CompositeValue;
import psObjects.values.reference.LocalRef;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.frame.PSDrawer;
import runtime.graphics.frame.PSFrame;
import runtime.graphics.frame.PSImage;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.PSPath;

import java.awt.*;

public class GState extends CompositeValue {
//    public static GState instance = new GState();

    public PSPoint currentPoint;
    public PSPath currentPath;
    public PSPath clippingPath;
    public TransformMatrix cTM;
    public PSObject font;
    public GraphicsSettings graphicsSettings;
    private boolean madeByGSaveOp;//save call gsave with false arg


    public GState() {
        currentPath = new PSPath();
        currentPoint = new PSPoint();
        cTM = new TransformMatrix();
        initClip();
        graphicsSettings = new GraphicsSettings();
        PSDrawer.reset();
        PSImage.reset();
        PSFrame.reset();
    }

    public GState(PSPath curPath, PSPath clipPath, TransformMatrix tM, GraphicsSettings gSettings, PSPoint curPoint, PSObject fontObj) {
        currentPath = curPath;
        clippingPath = clipPath;
        cTM = tM;
        graphicsSettings = gSettings;
        madeByGSaveOp = true; // for example
        currentPoint = curPoint;
        font = fontObj;
    }
//    public static GState getInstance() {
//        return instance;
//    }

    public static double psUnitToPixel(double psUnits) {
        return psUnits / 72 * 96;
        //96 - это число пикселей в дюйме
    }


    public LocalRef getLocalRefCTM() {
        if (!(cTM.getMatrix().getDirectValue() instanceof LocalRef)) return null;
        LocalRef ref = (LocalRef) cTM.getMatrix().getDirectValue();
        return ref;
    }


//    public LocalRef getLocalRefDash() {
//        if (!(graphicsSettings.dash.getDirectValue() instanceof LocalRef)) return null;
//        LocalRef ref = (LocalRef) graphicsSettings.dash.getDirectValue();
//        return ref;
//    }

    public GState getSnapshot() {
        return new GState(currentPath.clone(), clippingPath.clone(), cTM.clone(), cloneGraphicsSettings(), currentPoint, font);
    }

    //---------------------Fonts

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

    @Override
    public Type determineType() {
        return Type.GSTATE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GState)) return false;
        return true;
    }

    @Override
    public String toStringView(PSObject obj) {
        return "--gstate--";
    }

    public boolean isMadeByGSaveOp() {
        return madeByGSaveOp;
    }

    public void setMadeByGSaveOp(boolean madeByGSaveOp) {
        this.madeByGSaveOp = madeByGSaveOp;
    }
}
