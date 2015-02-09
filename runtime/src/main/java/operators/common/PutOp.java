package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.CompositeValue;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

public class PutOp extends Operator {

    public static final PutOp instance = new PutOp();

    protected PutOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject value = runtime.popFromOperandStack();
        if (value == null)
            return;
        PSObject key = runtime.popFromOperandStack();
        if (key == null) {
            runtime.pushToOperandStack(value);
            return;
        }
        PSObject src = runtime.popFromOperandStack();
        if (src == null) {
            runtime.pushToOperandStack(key);
            runtime.pushToOperandStack(value);
            return;
        }
        CompositeValue result;
        switch (src.getType()) {
            case DICTIONARY:
                src.setValue(((PSDictionary) src.getValue()).put(key, value));
                break;
            case ARRAY:
            case PACKEDARRAY:
                copyArray(value, key, src);
                break;
            case STRING:
                putString(src, key, value);
                break;
            default: {
                runtime.pushToOperandStack(src);
                runtime.pushToOperandStack(key);
                runtime.pushToOperandStack(value);
                return;
            }
        }

    }

    private void copyArray(PSObject value, PSObject key, PSObject src) {
        if (key.getType() == Type.INTEGER) {
            int index = ((PSInteger) key.getValue()).getIntValue();
            src.setValue(((PSArray) src.getValue()).setValue(index, value));
        } else {
            runtime.pushToOperandStack(src);
            runtime.pushToOperandStack(key);
            runtime.pushToOperandStack(value);
            return;
        }
    }

    private void putString(PSObject src, PSObject key, PSObject value) {

        boolean isChar = value.getType() == Type.STRING && ((PSString) value.getValue()).length() == 1;
        if (key.getType() != Type.INTEGER || !(value.getType() == Type.INTEGER || isChar)) {
            runtime.pushToOperandStack(src);
            runtime.pushToOperandStack(key);
            runtime.pushToOperandStack(value);
            return;
        }
        int iIndex = ((PSInteger) key.getValue()).getIntValue();
        byte character;
        if (isChar) {
            character = ((byte) (((PSString) value.getValue()).get(0)).getIntValue());
        } else {
            character = (byte) ((PSInteger) value.getValue()).getIntValue();
        }

        PSString string = (PSString) src.getValue();
        src.setValue(string.put(iIndex, character));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("put");
    }
}
