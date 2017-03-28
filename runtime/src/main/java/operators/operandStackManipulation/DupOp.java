package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class DupOp extends Operator {
    public static final DupOp instance = new DupOp();

    protected DupOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) {
            return;
        }
        PSObject dupObj = new PSObject(o.getDirectValue(), o.getType(), o.getAttribute());
        context.pushToOperandStack(o);
        context.pushToOperandStack(dupObj);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("dup");
    }
}
