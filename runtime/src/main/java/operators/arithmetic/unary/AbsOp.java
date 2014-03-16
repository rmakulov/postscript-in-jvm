package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class AbsOp extends Operator  {
    public static final AbsOp instance = new AbsOp();

    protected AbsOp() {
        super();
    }
    @Override
    public void execute() {
        UnaryArithmeticOp.doOperation(getSymbolicChar);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("abs");
    }


    public final static char getSymbolicChar = 'A';
}
