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
import runtime.Context;

public class PutOp extends Operator {

    public static final PutOp instance = new PutOp();

    protected PutOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject value = context.popFromOperandStack();
        if (value == null)
            return;
        PSObject key = context.popFromOperandStack();
        if (key == null) {
            context.pushToOperandStack(value);
            return;
        }
        PSObject src = context.popFromOperandStack();
        if (src == null) {
            context.pushToOperandStack(key);
            context.pushToOperandStack(value);
            return;
        }
        CompositeValue result;
        switch (src.getType()) {
            case DICTIONARY:
                src.setValue(((PSDictionary) src.getValue()).put(key, value));
                break;
            case ARRAY:
            case PACKEDARRAY:
                copyArray(context, value, key, src);
                break;
            case STRING:
                putString(context, src, key, value);
                break;
            default: {
                context.pushToOperandStack(src);
                context.pushToOperandStack(key);
                context.pushToOperandStack(value);
                return;
            }
        }

    }

    private void copyArray(Context context, PSObject value, PSObject key, PSObject src) {
        if (key.getType() == Type.INTEGER) {
            int index = ((PSInteger) key.getValue()).getIntValue();
            src.setValue(((PSArray) src.getValue()).setValue(index, value));
        } else {
            context.pushToOperandStack(src);
            context.pushToOperandStack(key);
            context.pushToOperandStack(value);
            return;
        }
    }

    private void putString(Context context, PSObject src, PSObject key, PSObject value) {

        boolean isChar = value.getType() == Type.STRING && ((PSString) value.getValue()).length() == 1;
        if (key.getType() != Type.INTEGER || !(value.getType() == Type.INTEGER || isChar)) {
            context.pushToOperandStack(src);
            context.pushToOperandStack(key);
            context.pushToOperandStack(value);
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
