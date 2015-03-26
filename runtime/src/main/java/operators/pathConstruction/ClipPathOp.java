

package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 16.03.14.
 */
public class ClipPathOp extends AbstractGraphicOperator {
    public static final ClipPathOp instance = new ClipPathOp();

    protected ClipPathOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.getGState().currentPath = context.getGState().clippingPath.clone();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("clippath");
    }
}
