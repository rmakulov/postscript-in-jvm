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

/**
 * Created by user on 02.04.14.
 */
public class CopyOp extends Operator {
    public static final CopyOp instance = new CopyOp();

    protected CopyOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) {
            return;
        }
        PSObject result;
        switch (o.getType()) {
            case INTEGER:
                copyElements(context, o);
                break;
            case DICTIONARY:
                copyDictionary(context, o);
                break;
            case ARRAY:
                copyArray(context, o);
                break;
            case STRING:
                copyString(context, o);
                break;
            default: {
                context.pushToOperandStack(o);
            }
        }
    }

    private void copyElements(Context context, PSObject num) {
        PSInteger psInt = (PSInteger) num.getValue();
        int n = psInt.getIntValue();
        if (context.getOperandStackSize() < n) {
            context.pushToOperandStack(num);
            return;
        }
        PSObject[] objects = new PSObject[n];
        for (int i = n - 1; i >= 0; i--) {
            objects[i] = context.popFromOperandStack();
        }
        for (int i = 0; i < n; i++) {
            context.pushToOperandStack(objects[i]);
        }
        for (int i = 0; i < n; i++) {
            context.pushToOperandStack(objects[i].copy());
        }
    }

    private void copyArray(Context context, PSObject o1) {
        if (context.getOperandStackSize() < 1) {
            context.pushToOperandStack(o1);
            return;
        }
        PSObject o2 = context.popFromOperandStack();
        if (o2.getType() != Type.ARRAY) {
            context.pushToOperandStack(o2);
            context.pushToOperandStack(o1);
            return;
        }
        PSArray dst = (PSArray) o2.getValue();
        PSArray src = (PSArray) o1.getValue();
        if (dst.length() < src.length()) {
            context.pushToOperandStack(o2);
            context.pushToOperandStack(o1);
            return;
        }
        o2.setValue(src.copy());
        context.pushToOperandStack(o2);
    }

    private void copyString(Context context, PSObject o1) {
        if (context.getOperandStackSize() < 1) {
            context.pushToOperandStack(o1);
            return;
        }
        PSObject o2 = context.popFromOperandStack();
        if (o2.getType() != Type.STRING) {
            context.pushToOperandStack(o2);
            context.pushToOperandStack(o1);
            return;
        }
        PSString dst = (PSString) o2.getValue();
        PSString src = (PSString) o1.getValue();
        if (dst.length() < src.length()) {
            context.pushToOperandStack(o2);
            context.pushToOperandStack(o1);
            return;
        }
        o2.setValue(src.copy());
        context.pushToOperandStack(o2);
    }

    private void copyDictionary(Context context, PSObject dict2) {
        if (context.getOperandStackSize() < 1) return;
        PSObject dict1 = context.popFromOperandStack();
        if (dict1.getType() != Type.DICTIONARY) {
            context.pushToOperandStack(dict1);
            context.pushToOperandStack(dict2);
            return;
        }
        PSDictionary psDict1 = (PSDictionary) dict1.getValue();
        PSDictionary psDict2 = (PSDictionary) dict2.getValue();
        dict2.setValue(psDict1.copy(psDict2));
        context.pushToOperandStack(dict2);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("copy");
    }
}
