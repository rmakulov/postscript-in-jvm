package operators.arithmetic.binary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class AtanOp extends Operator {
    public static final AtanOp instance = new AtanOp();

    protected AtanOp() {
        super();
    }
    @Override
    public void execute() {
        BinaryArithmeticOp.doOperation('t');
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("atan");
    }

    public final static char getSymbolicChar = 't';
}
