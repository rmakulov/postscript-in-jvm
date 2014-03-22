package operators.painting;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.paths.PSPath;

/**
 * Created by user on 16.03.14.
 */
public class FillOp extends AbstractGraphicOperator {
    public static final FillOp instance = new FillOp();

    protected FillOp() {
        super();
    }

    @Override
    public void execute() { // Fill current path with current color
        gState.currentPath.setGraphicsSettings(gState.cloneGraphicsSettings());
        gState.currentPath.setPaintingState(PSPath.PaintingState.FILL);
        gState.addCurrentPathInPaintingPaths();
        gState.currentPath = null;
        gState.currentPoint = null;
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("fill");
    }
}
