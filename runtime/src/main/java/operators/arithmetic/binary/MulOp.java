package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class MulOp extends Operator {
    public static final MulOp instance = new MulOp();

    protected MulOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        BinaryArithmeticOp.doOperation(context, '*');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("mul");
    }
}
