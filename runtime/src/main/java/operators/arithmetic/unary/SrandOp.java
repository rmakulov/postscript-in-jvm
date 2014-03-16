package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class SrandOp extends Operator {
    public static final SrandOp instance = new SrandOp();

    protected SrandOp() {
        super();
    }
     //todo I haven't understood what does this operator do
    @Override
    public void execute() {
        //todo
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("srand");
    }

    public final static char getSymbolicChar = 'r';
}
