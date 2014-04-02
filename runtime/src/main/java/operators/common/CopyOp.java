package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by user on 02.04.14.
 */
public class CopyOp extends Operator {
    public static final CopyOp instance = new CopyOp();

    protected CopyOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) {
            return;
        }
        PSObject result;
        switch (o.getType()) {
            case INTEGER:
                copyElements(o);
                break;
            case DICTIONARY:
                copyDictionary(o);
                break;
            case ARRAY:
                copyArray(o);
                break;
            case STRING:
                copyString(o);
                break;
            default: {
                runtime.pushToOperandStack(o);
            }
        }
    }

    private void copyElements(PSObject num) {
        PSInteger psInt = (PSInteger) num.getValue();
        int n = psInt.getIntValue();
        if (runtime.getOperandStackSize() < n) {
            runtime.pushToOperandStack(num);
            return;
        }
        PSObject[] objects = new PSObject[n];
        for (int i = n - 1; i >= 0; i--) {
            objects[i] = runtime.popFromOperandStack();
        }
        for (int i = 0; i < n; i++) {
            runtime.pushToOperandStack(objects[i]);
        }
        for (int i = 0; i < n; i++) {
            runtime.pushToOperandStack(objects[i].copy());
        }
    }

    private void copyArray(PSObject o1) {
        if (runtime.getOperandStackSize() < 1) {
            runtime.pushToOperandStack(o1);
            return;
        }
        PSObject o2 = runtime.popFromOperandStack();
        if (o2.getType() != Type.ARRAY) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            return;
        }
        PSArray dst = (PSArray) o2.getValue();
        PSArray src = (PSArray) o1.getValue();
        if (dst.length() < src.length()) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            return;
        }
        o2.setValue(src.copy());
        runtime.pushToOperandStack(o2);
    }

    private void copyString(PSObject o1) {
        if (runtime.getOperandStackSize() < 1) {
            runtime.pushToOperandStack(o1);
            return;
        }
        PSObject o2 = runtime.popFromOperandStack();
        if (o2.getType() != Type.STRING) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            return;
        }
        PSString dst = (PSString) o2.getValue();
        PSString src = (PSString) o1.getValue();
        if (dst.length() < src.length()) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            return;
        }
        o2.setValue(src.copy());
        runtime.pushToOperandStack(o2);
    }

    private void copyDictionary(PSObject dict2) {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject dict1 = runtime.popFromOperandStack();
        if (dict1.getType() != Type.DICTIONARY) {
            runtime.pushToOperandStack(dict1);
            runtime.pushToOperandStack(dict2);
            return;
        }
        PSDictionary psDict1 = (PSDictionary) dict1.getValue();
        PSDictionary psDict2 = (PSDictionary) dict2.getValue();
        dict2.setValue(psDict1.copy(psDict2));
        runtime.pushToOperandStack(dict2);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("copy");
    }
}
