package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class ModOp extends Operator {
    public static final ModOp instance = new ModOp();

    protected ModOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        BinaryIntArithmeticOp.doOperation(context, '%');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("mod");
    }
}
