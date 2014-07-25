package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class SinOp extends Operator {
    public static final SinOp instance = new SinOp();

    protected SinOp() {
        super();
    }
    @Override
    public void execute() {
        UnaryArithmeticOp.doOperation(getSymbolicChar);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("sin");
    }

    public final static char getSymbolicChar = 's';
}
