package operators.dictionary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class KnownOp extends Operator {
    public static final KnownOp instance = new KnownOp();

    protected KnownOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject key = context.popFromOperandStack();
        PSObject dict = context.popFromOperandStack();
        if (!key.isDictKey() || dict.getType() != Type.DICTIONARY) {
            context.pushToOperandStack(dict);
            context.pushToOperandStack(key);
            return;
        }
        PSDictionary psDictionary = (PSDictionary) dict.getValue();
        if (psDictionary.get(key) == null) {
            context.pushToOperandStack(new PSObject(PSBoolean.FALSE));
        } else {
            context.pushToOperandStack(new PSObject(PSBoolean.TRUE));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("known");
    }
}
