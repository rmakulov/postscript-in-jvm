package operators.arithmetic.binary;

import operators.arithmetic.binary.BinaryArithmeticOp;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class SubOp extends Operator {
    @Override
    public void execute() {
      BinaryArithmeticOp.doOperation('-');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("sub");
    }
}
