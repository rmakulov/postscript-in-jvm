package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

public class GetIntervalOp extends Operator {

    public static final GetIntervalOp instance = new GetIntervalOp();

    protected GetIntervalOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject count = runtime.popFromOperandStack();
        if (count == null)
            return;
        PSObject index = runtime.popFromOperandStack();
        if (index == null) {
            runtime.pushToOperandStack(count);
            return;
        }
        PSObject src = runtime.popFromOperandStack();
        if (src == null) {
            runtime.pushToOperandStack(index);
            runtime.pushToOperandStack(count);
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
                    runtime.pushToOperandStack(src);
                    runtime.pushToOperandStack(index);
                    runtime.pushToOperandStack(count);
                    return;
                }
            }
            runtime.pushToOperandStack(new PSObject(result, src.getType(), src.getAttribute()));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("getinterval");
    }
}
