package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.paths.PSPath;

/**
 * Created by user on 16.03.14.
 */
public class ClipPathOp extends AbstractGraphicOperator {
    public static final ClipPathOp instance = new ClipPathOp();

    protected ClipPathOp() {
        super();
    }

    @Override
    public void execute() {
        gState.currentPath = gState.clippingPath.clone();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("clippath");
    }
}