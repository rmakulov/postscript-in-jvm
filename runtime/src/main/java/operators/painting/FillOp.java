package operators.painting;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.paths.DrawPath;
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
        gState.addCurrentPathInDrawPath(DrawPath.PaintingState.FILL);
        gState.currentPath = new PSPath();
        gState.currentPoint = null;
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("fill");
    }
}
