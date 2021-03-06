package operators.common;

import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

public class LengthOp extends Operator {

    public static final LengthOp instance = new LengthOp();

    protected LengthOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject psObject = context.popFromOperandStack();
        int length;
        switch (psObject.getType()) {
            case DICTIONARY:
                length = ((PSDictionary) psObject.getValue()).getTree().getCount();
                break;
            case ARRAY:             // todo get length in psarray
            case PACKEDARRAY:
                length = ((PSArray) psObject.getValue()).getArray().length;
                break;
            case STRING:
                length = ((PSString) psObject.getValue()).length();
                break;
            case NAME:
                length = ((PSName) psObject.getValue()).length();
                break;
            default: {
                context.pushToOperandStack(psObject);
                return;
            }
        }
        context.pushToOperandStack(new PSObject(new PSInteger(length)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("length");
    }
}
