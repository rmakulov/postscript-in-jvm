package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

public class GetOp extends Operator {

    public static final GetOp instance = new GetOp();

    protected GetOp() {
        super();
    }

    // todo delete from runtime get operator
    @Override
    public void interpret(Context context) {
        PSObject key = context.popFromOperandStack();
        if (key == null)
            return;
        PSObject src = context.popFromOperandStack();
        if (src == null) {
            context.pushToOperandStack(key);
            return;
        }
        PSObject result;
        switch (src.getType()) {
            case DICTIONARY:
                result = ((PSDictionary) src.getValue()).get(key);
                break;
            case ARRAY:
            case PACKEDARRAY:
                if (key.getType() == Type.INTEGER) {
                    int index = ((PSInteger) key.getValue()).getIntValue();
                    result = ((PSArray) src.getValue()).get(index);
                } else {
                    context.pushToOperandStack(src);
                    context.pushToOperandStack(key);
                    return;
                }
                break;
            case STRING:
                if (key.getType() == Type.INTEGER) {
                    int index = ((PSInteger) key.getValue()).getIntValue();
                    PSString psString = (PSString) src.getValue();
                    result = new PSObject(psString.get(index));
                } else {
                    context.pushToOperandStack(src);
                    context.pushToOperandStack(key);
                    return;
                }
                break;

            default: {
                context.pushToOperandStack(src);
                context.pushToOperandStack(key);
                fail();
                return;
            }

        }
        runtime.pushToOperandStack(result);

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("get");
    }
}
