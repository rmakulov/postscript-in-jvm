package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by Дмитрий on 05.04.14.
 */
public class CountOp extends Operator {
    public static final CountOp instance = new CountOp();

    protected CountOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.pushToOperandStack(new PSObject(new PSInteger(context.getOperandStackSize())));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("count");
    }
}
