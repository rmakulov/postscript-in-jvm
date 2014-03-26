package operators.dictionary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class CopyOp extends Operator {
    public static final CopyOp instance = new CopyOp();

    protected CopyOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject dict2 = runtime.popFromOperandStack();
        PSObject dict1 = runtime.popFromOperandStack();
        if (dict2.getType() != Type.DICTIONARY || dict1.getType() != Type.DICTIONARY) {
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
