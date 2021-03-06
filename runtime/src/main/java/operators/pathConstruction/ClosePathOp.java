package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 15.03.14.
 */
public class ClosePathOp extends AbstractGraphicOperator {
    public static final ClosePathOp instance = new ClosePathOp();

    protected ClosePathOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.getGState().currentPath.closePath();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("closepath");
    }
}
