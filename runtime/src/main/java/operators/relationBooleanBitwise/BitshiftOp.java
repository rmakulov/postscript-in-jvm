package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class BitshiftOp extends Operator {

    public static final BitshiftOp instance = new BitshiftOp();

    protected BitshiftOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject o2 = context.popFromOperandStack();
        PSObject o1 = context.popFromOperandStack();
        if (o1.getType() != Type.INTEGER || o2.getType() != Type.INTEGER) {
            context.pushToOperandStack(o1);
            context.pushToOperandStack(o2);
        }
        int num = ((PSInteger) o1.getValue()).getIntValue();
        int shift = ((PSInteger) o2.getValue()).getIntValue();
        int res;
        if (shift > 0) {
            res = num << shift;
        } else {
            res = num >> (-shift);
        }
        context.pushToOperandStack(new PSObject(new PSInteger(res)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("bitshift");
    }
}
