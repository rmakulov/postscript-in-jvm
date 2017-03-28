package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class IdivOp extends Operator {
    public static final IdivOp instance = new IdivOp();

    protected IdivOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        BinaryIntArithmeticOp.doOperation(context, '/');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("idiv");
    }
}
