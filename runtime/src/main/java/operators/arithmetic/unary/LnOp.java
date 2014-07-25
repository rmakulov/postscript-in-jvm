package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class LnOp extends Operator {
    public static final LnOp instance = new LnOp();

    protected LnOp() {
        super();
    }
    @Override
    public void execute() {
        UnaryArithmeticOp.doOperation(getSymbolicChar);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("ln");
    }

    public final static char getSymbolicChar='L';
}
