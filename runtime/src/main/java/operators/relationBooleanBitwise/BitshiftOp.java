package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class BitshiftOp extends Operator {

    public static final BitshiftOp instance = new BitshiftOp();

    protected BitshiftOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject o2 = runtime.popFromOperandStack();
        PSObject o1 = runtime.popFromOperandStack();
        if (o1.getType() != Type.INTEGER || o2.getType() != Type.INTEGER) {
            runtime.pushToOperandStack(o1);
            runtime.pushToOperandStack(o2);
        }
        int num = ((PSInteger) o1.getValue()).getIntValue();
        int shift = ((PSInteger) o2.getValue()).getIntValue();
        int res;
        if (shift > 0) {
            res = num << shift;
        } else {
            res = num >> (-shift);
        }
        runtime.pushToOperandStack(new PSObject(new PSInteger(res)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("bitshift");
    }
}
