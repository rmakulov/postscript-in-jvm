package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 16.03.14.
 */
public class GRestoreOp extends AbstractGraphicOperator {
    public static final GRestoreOp instance = new GRestoreOp();

    protected GRestoreOp() {
        super();
    }

    /*it's too hard to explain, but we try
    * if last gstate was made by save*/
    @Override
    public void interpret(Context context) {
        context.removeFromGraphicStack();
        if (!context.getGState().isMadeByGSaveOp()) {
            runtime.gsave(context, false);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("grestore");
    }
}
