package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class SqrtOp extends Operator {
    public static final SqrtOp instance = new SqrtOp();

    protected SqrtOp() {
        super();
    }

    @Override
    public void interpret() {
        UnaryArithmeticOp.doOperation(getSymbolicChar);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("sqrt");
    }

    public final static char getSymbolicChar = 'S';
}
