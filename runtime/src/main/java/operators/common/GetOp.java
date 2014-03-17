package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.dictionaries.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

public class GetOp extends Operator {

    public static final GetOp instance = new GetOp();

    protected GetOp() {
        super();
    }

    // todo delete from runtime get operator
    @Override
    public void execute() {
        PSObject key = runtime.popFromOperandStack();
        if (key == null)
            return;
        PSObject src = runtime.popFromOperandStack();
        if (src == null) {
            runtime.pushToOperandStack(key);
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
                    runtime.pushToOperandStack(src);
                    runtime.pushToOperandStack(key);
                    return;
                }
                break;
            case STRING:
                if (key.getType() == Type.INTEGER) {
                    int index = ((PSInteger) key.getValue()).getIntValue();
                    PSString psString = (PSString) src.getValue();
                    result = new PSObject(psString.get(index));
                } else {
                    runtime.pushToOperandStack(src);
                    runtime.pushToOperandStack(key);
                    return;
                }
                break;

            default: {
                runtime.pushToOperandStack(src);
                runtime.pushToOperandStack(key);
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
