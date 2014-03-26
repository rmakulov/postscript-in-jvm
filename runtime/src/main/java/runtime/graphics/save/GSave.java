package runtime.graphics.save;

import runtime.graphics.GraphicsSettings;
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
    public int flatness;

    public PSPoint getCurrentPoint() {
        return currentPoint;
    }

    public GraphicsSettings getSettings() {
        return settings;
    }

    public TransformMatrix getcTM() {
        return cTM;
    }

    public PSPath getClippingPath() {
        return clippingPath;
    }

    public PSPath getCurrentPath() {
        return currentPath;
    }

    private PSPoint currentPoint;

    public GSave(boolean madeByGSave) {
        madeByGSaveOp = madeByGSave;
    }

    public GSave(PSPath curPath, PSPath clipPath, TransformMatrix tM, GraphicsSettings gSettings, boolean byGSaveOp, PSPoint curPoint, int flatness) {
        currentPath = curPath;
        clippingPath = clipPath;
        cTM = tM;
        settings = gSettings;
        madeByGSaveOp = byGSaveOp;
        currentPoint = curPoint;
        this.flatness = flatness;
    }

    public boolean isMadeByGSaveOp() {
        return madeByGSaveOp;
    }
}
