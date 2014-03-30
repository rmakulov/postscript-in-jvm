package operators.arithmetic.unary;

import operators.arithmetic.RandOp;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class SrandOp extends Operator {
    public static final SrandOp instance = new SrandOp();

    protected SrandOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null || o.getType() != Type.INTEGER) {
            runtime.pushToOperandStack(o);
            return;
        }
        PSInteger psInteger = (PSInteger) o.getValue();
        int seed = psInteger.getIntValue();
        RandOp.setRandomSeed(seed);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("srand");
    }

    public final static char getSymbolicChar = 'r';
}
