package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 27.09.14.
 */
public class ClearToMarkOp extends Operator {
    public static final ClearToMarkOp instance = new ClearToMarkOp();

    protected ClearToMarkOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject psObj = runtime.peekFromOperandStack();
        if (checkNull(psObj)) return;
        while (psObj.getType() != Type.MARK) {
            runtime.popFromOperandStack();
            psObj = runtime.peekFromOperandStack();
            if (checkNull(psObj)) return;
        }
        runtime.popFromOperandStack();
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
