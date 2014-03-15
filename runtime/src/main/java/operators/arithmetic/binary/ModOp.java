package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class ModOp extends Operator {
    @Override
    public void execute() {
        BinaryIntArithmeticOp.doOperation('%');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("mod");
    }
}
