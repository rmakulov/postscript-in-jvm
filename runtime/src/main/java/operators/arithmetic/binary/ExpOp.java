package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class ExpOp extends Operator {
    public static final ExpOp instance = new ExpOp();

    protected ExpOp() {
        super();
    }

    @Override
    public void interpret() {
        BinaryArithmeticOp.doOperation('e');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exp");
    }
}
