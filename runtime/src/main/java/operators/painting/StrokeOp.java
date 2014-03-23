package operators.painting;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.paths.DrawPath;
import runtime.graphics.paths.PSPath;

/**
 * Created by user on 16.03.14.
 */
public class StrokeOp extends AbstractGraphicOperator {
    public static final StrokeOp instance = new StrokeOp();

    protected StrokeOp() {
        super();
    }

    @Override
    public void execute() { //-- setGraphicsSettings --
        gState.addCurrentPathInDrawPath(DrawPath.PaintingState.STROKE);
        gState.currentPath = new PSPath();
        gState.currentPoint = null;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("stroke");
    }
}
