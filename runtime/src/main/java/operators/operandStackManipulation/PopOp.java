package operators.operandStackManipulation;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class PopOp extends Operator {
    public static final PopOp instance = new PopOp();

    protected PopOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) {
            fail();
            return;
        }
        context.popFromOperandStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("pop");
    }
}
