package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class FloorOp extends Operator {
    public static final FloorOp instance = new FloorOp();

    protected FloorOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        UnaryArithmeticOp.doOperation(context, getSymbolicChar);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("floor");
    }

    public final static char getSymbolicChar = '_';
}
