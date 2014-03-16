package operators.arithmetic.binary;

import operators.arithmetic.binary.BinaryArithmeticOp;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class MulOp extends Operator {
    @Override
    public void execute() {
         BinaryArithmeticOp.doOperation('*');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("mul");
    }
}
