package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class RoundOp extends Operator {
    public static final RoundOp instance = new RoundOp();

    protected RoundOp() {
        super();
    }

    @Override
    public void interpret() {
        UnaryArithmeticOp.doOperation(getSymbolicChar);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("round");
    }

    public final static char getSymbolicChar = '~';
}
