package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class ExchOp extends Operator {
    public static final ExchOp instance = new ExchOp();

    protected ExchOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 2) fail();
        PSObject o2 = runtime.popFromOperandStack();
        PSObject o1 = runtime.popFromOperandStack();
        runtime.pushToOperandStack(o2);
        runtime.pushToOperandStack(o1);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exch");
    }
}
