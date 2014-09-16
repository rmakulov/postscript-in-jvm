package operators.dictionary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class KnownOp extends Operator {
    public static final KnownOp instance = new KnownOp();

    protected KnownOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject key = runtime.popFromOperandStack();
        PSObject dict = runtime.popFromOperandStack();
        if (!key.isDictKey() || dict.getType() != Type.DICTIONARY) {
            runtime.pushToOperandStack(dict);
            runtime.pushToOperandStack(key);
            return;
        }
        PSDictionary psDictionary = (PSDictionary) dict.getValue();
        if (psDictionary.get(key) == null) {
            runtime.pushToOperandStack(new PSObject(PSBoolean.FALSE));
        } else {
            runtime.pushToOperandStack(new PSObject(PSBoolean.TRUE));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("known");
    }
}
