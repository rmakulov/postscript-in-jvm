package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.values.Value;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class EqOp extends Operator {

    public static final EqOp instance = new EqOp();

    protected EqOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject o2 = context.popFromOperandStack();
        PSObject o1 = context.popFromOperandStack();
        Value v1 = o1.getValue();
        Value v2 = o2.getValue();
        context.pushToOperandStack(new PSObject(PSBoolean.get(v1.equals(v2))));
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("eq");
    }
}
