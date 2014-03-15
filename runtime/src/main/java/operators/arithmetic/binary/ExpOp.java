package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class ExpOp extends Operator {
    @Override
    public void execute() {
        BinaryArithmeticOp.doOperation('e');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exp");
    }
}
