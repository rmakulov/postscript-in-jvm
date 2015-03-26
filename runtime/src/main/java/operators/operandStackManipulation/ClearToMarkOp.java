package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 27.09.14.
 */
public class ClearToMarkOp extends Operator {
    public static final ClearToMarkOp instance = new ClearToMarkOp();

    protected ClearToMarkOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject psObj = context.peekFromOperandStack();
        if (checkNull(psObj)) return;
        while (psObj.getType() != Type.MARK) {
            context.popFromOperandStack();
            psObj = context.peekFromOperandStack();
            if (checkNull(psObj)) return;
        }
        context.popFromOperandStack();
    }

    private boolean checkNull(PSObject psObj) {
        if (psObj == null) {
            fail();
            return true;
        }
        return false;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cleartomark");
    }
}
