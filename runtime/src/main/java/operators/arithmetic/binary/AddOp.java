package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

public class AddOp extends Operator {
    public static final AddOp instance = new AddOp();

    protected AddOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        BinaryArithmeticOp.doOperation(context, '+');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("add");
    }
}
