package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

public class PutOp extends Operator {
    @Override
    public void execute() {
        PSObject value = runtime.popFromOperandStack();
        if (value == null)
            return;
        PSObject key = runtime.popFromOperandStack();
        if (key == null){
            runtime.pushToOperandStack(value);
            return;
        }
        PSObject src = runtime.popFromOperandStack();
        if (src == null){
            runtime.pushToOperandStack(key);
            runtime.pushToOperandStack(value);
            return;
        }
        CompositeValue result;
        switch (src.getType()) {
            case DICTIONARY:
                result = ((PSDictionary) src.getValue()).put(key, value);
                break;
            case ARRAY:
            case PACKEDARRAY:
                if (key.getType() == Type.INTEGER) {
                    int index = ((PSInteger) key.getValue()).getIntValue();
                    result = ((PSArray) src.getValue()).setValue(index, value);
                }
                else {
                    runtime.pushToOperandStack(src);
                    runtime.pushToOperandStack(key);
                    runtime.pushToOperandStack(value);
                    return;
                }
                break;
            case STRING: //todo put integer into string
            default: {
                runtime.pushToOperandStack(src);
                runtime.pushToOperandStack(key);
                runtime.pushToOperandStack(value);
                return;
            }

        }
        src.setValue(result);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("put");
    }
}
