package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.values.Value;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class EqOp extends Operator {

    public static final EqOp instance = new EqOp();

    protected EqOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject o2 = runtime.popFromOperandStack();
        PSObject o1 = runtime.popFromOperandStack();
        Value v1 = o1.getValue();
        Value v2 = o2.getValue();
        runtime.pushToOperandStack(new PSObject(PSBoolean.get(v1.equals(v2))));
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("eq");
    }
}
