package operators.arithmetic.binary;

import operators.arithmetic.binary.BinaryArithmeticOp;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class DivOp extends Operator {
    @Override
    public void execute() {
        BinaryArithmeticOp.doOperation('/');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("div");
    }

}
