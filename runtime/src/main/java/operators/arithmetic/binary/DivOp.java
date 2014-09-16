package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class DivOp extends Operator {
    public static final DivOp instance = new DivOp();

    protected DivOp() {
        super();
    }

    @Override
    public void interpret() {
        BinaryArithmeticOp.doOperation('/');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("div");
    }

}
