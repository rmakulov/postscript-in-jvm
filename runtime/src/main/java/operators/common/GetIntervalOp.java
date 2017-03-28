package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

public class GetIntervalOp extends Operator {

    public static final GetIntervalOp instance = new GetIntervalOp();

    protected GetIntervalOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject count = context.popFromOperandStack();
        if (count == null)
            return;
        PSObject index = context.popFromOperandStack();
        if (index == null) {
            context.pushToOperandStack(count);
            return;
        }
        PSObject src = context.popFromOperandStack();
        if (src == null) {
            context.pushToOperandStack(index);
            context.pushToOperandStack(count);
            return;
        }
        if (index.getType() == Type.INTEGER && count.getType() == Type.INTEGER) {
            int start = ((PSInteger) index.getValue()).getIntValue();
            int length = ((PSInteger) count.getValue()).getIntValue();
            Value result;
            switch (src.getType()) {
                case ARRAY:
                case PACKEDARRAY:
                    result = ((PSArray) src.getValue()).getInterval(start, length);
                    break;
                case STRING:
                    result = ((PSString) src.getValue()).getInterval(start, length);
                    break;
                default: {
                    context.pushToOperandStack(src);
                    context.pushToOperandStack(index);
                    context.pushToOperandStack(count);
                    return;
                }
            }
            context.pushToOperandStack(new PSObject(result, src.getType(), src.getAttribute()));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("getinterval");
    }
}
