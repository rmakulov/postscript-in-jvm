package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class CosOp extends Operator {
    public static final CosOp instance = new CosOp();

    protected CosOp() {
        super();
    }

    @Override
    public void interpret() {
        UnaryArithmeticOp.doOperation(getSymbolicChar);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cos");
    }

    public final static char getSymbolicChar = 'C';
}
