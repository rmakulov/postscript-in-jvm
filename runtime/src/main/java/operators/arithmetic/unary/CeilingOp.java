package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class CeilingOp extends Operator {
    public static final CeilingOp instance = new CeilingOp();

    protected CeilingOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        UnaryArithmeticOp.doOperation(context, getSymbolicChar);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("ceiling");
    }

    public final static char getSymbolicChar = '^';
}
