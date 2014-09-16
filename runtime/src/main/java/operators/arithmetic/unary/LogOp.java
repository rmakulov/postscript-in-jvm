package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class LogOp extends Operator {
    public static final LogOp instance = new LogOp();

    protected LogOp() {
        super();
    }

    @Override
    public void interpret() {
        UnaryArithmeticOp.doOperation(getSymbolicChar);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("log");
    }

    public final static char getSymbolicChar = 'l';
}
