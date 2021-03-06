package operators.operandStackManipulation;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class ClearOp extends Operator {
    public static final ClearOp instance = new ClearOp();

    protected ClearOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.clearOperandStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("clear");
    }
}
