package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.Context;

/**
 * Created by user on 16.03.14.
 */
public class SetMiterLimitOp extends AbstractGraphicOperator {
    public static final SetMiterLimitOp instance = new SetMiterLimitOp();

    protected SetMiterLimitOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject oNum = context.popFromOperandStack();
        if (oNum == null || !oNum.isNumber()) {
            context.pushToOperandStack(oNum);
            return;
        }
        double nNum = ((PSNumber) oNum.getValue()).getRealValue();
        if (nNum < 1) {
            context.pushToOperandStack(oNum);
            return;
        }
        context.getGState().graphicsSettings.miterLimit = nNum;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setmiterlimit");
    }
}
