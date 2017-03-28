package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class ExchOp extends Operator {
    public static final ExchOp instance = new ExchOp();

    protected ExchOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) fail();
        PSObject o2 = context.popFromOperandStack();
        PSObject o1 = context.popFromOperandStack();
        context.pushToOperandStack(o2);
        context.pushToOperandStack(o1);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exch");
    }
}
