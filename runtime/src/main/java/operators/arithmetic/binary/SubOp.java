package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class SubOp extends Operator {
    public static final SubOp instance = new SubOp();

    protected SubOp() {
        super();
    }

    @Override
    public void interpret() {
        BinaryArithmeticOp.doOperation('-');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("sub");
    }
}
