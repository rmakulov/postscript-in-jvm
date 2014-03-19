package runtime.graphics.save;

import runtime.graphics.GraphicsSettings;
import runtime.graphics.GraphicsState;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.PSPath;

/**
 * Created by user on 16.03.14.
 */

public class GSave {
    private PSPath currentPath;
    private PSPath clippingPath;
    private TransformMatrix cTM;
    private GraphicsSettings settings;
    private boolean madeByGSaveOp;
    private PSPoint currentPoint;

    public GSave(boolean made) {
        madeByGSaveOp = made;
    }

    public boolean isMadeByGSaveOp() {
        return madeByGSaveOp;
    }

    public void setSnapshot() {
        GraphicsState inst = GraphicsState.getInstance();
        inst.graphicsSettings = settings;
        inst.clippingPath = clippingPath;
        inst.currentPath = currentPath;
        inst.currentPoint = currentPoint;
        inst.cTM = cTM;
    }

    public void getSnapshot() {
        GraphicsState inst = GraphicsState.getInstance();
        settings = inst.cloneGraphicsSettings();
        currentPath = inst.currentPath;
        clippingPath = inst.clippingPath;
        cTM = inst.cTM;
        currentPoint = inst.currentPoint;
    }

}
