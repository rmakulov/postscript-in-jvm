package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class NegOp extends Operator {
    public static final NegOp instance = new NegOp();

    protected NegOp() {
        super();
    }

    @Override
    public void interpret() {
        UnaryArithmeticOp.doOperation(getSymbolicChar);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("neg");
    }

    public final static char getSymbolicChar = '-';
}
