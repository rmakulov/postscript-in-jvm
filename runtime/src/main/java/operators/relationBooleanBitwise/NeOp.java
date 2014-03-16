package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.values.Value;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Runtime;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class NeOp extends Operator {

    public static final NeOp instance = new NeOp();

    protected NeOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 2) return;
        Runtime runtime = Runtime.getInstance();
        PSObject o2 = runtime.popFromOperandStack();
        if (o2 == null) return;
        PSObject o1 = runtime.popFromOperandStack();
        if (o1 == null) {
            runtime.pushToOperandStack(o2);
            return;
        }
        Value v1 = o1.getValue();
        Value v2 = o2.getValue();
        runtime.pushToOperandStack(new PSObject(PSBoolean.get(!v1.equals(v2))));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("ne");
    }
}
