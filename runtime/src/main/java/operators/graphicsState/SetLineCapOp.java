package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by user on 16.03.14.
 */
public class SetLineCapOp extends AbstractGraphicOperator {
    public static final SetLineCapOp instance = new SetLineCapOp();

    protected SetLineCapOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject oLC = runtime.popFromOperandStack();
        if (oLC == null || !oLC.isNumber()) {
            runtime.pushToOperandStack(oLC);
            return;
        }
        int nLC = ((PSInteger) oLC.getValue()).getIntValue();
        gState.graphicsSettings.lineCap = nLC;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setlinecap");
    }
}
