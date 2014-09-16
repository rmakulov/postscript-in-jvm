package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class AddOp extends Operator {
    public static final AddOp instance = new AddOp();

    protected AddOp() {
        super();
    }

    @Override
    public void interpret() {
        BinaryArithmeticOp.doOperation('+');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("add");
    }
}
