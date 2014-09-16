package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class StoreOp extends Operator {
    public static final StoreOp instance = new StoreOp();

    protected StoreOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject value = runtime.popFromOperandStack();
        PSObject key = runtime.popFromOperandStack();
        if (!key.isDictKey()) {
            runtime.pushToOperandStack(key);
            runtime.pushToOperandStack(value);
            return;
        }
        PSObject dictionaryObj = runtime.findDict(key);
        if (dictionaryObj == null) {
            runtime.pushToOperandStack(key);
            runtime.pushToOperandStack(value);
            return;
        }
        PSDictionary dict = (PSDictionary) dictionaryObj.getValue();
        dictionaryObj.setValue(dict.put(key, value));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("store");
    }

}
