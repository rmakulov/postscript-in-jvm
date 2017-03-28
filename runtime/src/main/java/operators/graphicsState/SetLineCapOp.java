package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by user on 16.03.14.
 */
public class SetLineCapOp extends AbstractGraphicOperator {
    public static final SetLineCapOp instance = new SetLineCapOp();

    protected SetLineCapOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject oLC = context.popFromOperandStack();
        if (oLC == null || !oLC.isNumber()) {
            context.pushToOperandStack(oLC);
            return;
        }
        int nLC = ((PSInteger) oLC.getValue()).getIntValue();
        context.getGState().graphicsSettings.lineCap = nLC;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setlinecap");
    }
}
