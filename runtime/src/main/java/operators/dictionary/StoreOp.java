package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class StoreOp extends Operator {
    public static final StoreOp instance = new StoreOp();

    protected StoreOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject value = context.popFromOperandStack();
        PSObject key = context.popFromOperandStack();
        if (!key.isDictKey()) {
            context.pushToOperandStack(key);
            context.pushToOperandStack(value);
            return;
        }
        PSObject dictionaryObj = context.findDict(key);
        if (dictionaryObj == null) {
            context.pushToOperandStack(key);
            context.pushToOperandStack(value);
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
