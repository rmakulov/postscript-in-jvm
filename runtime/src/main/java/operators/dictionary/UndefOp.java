package operators.dictionary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class UndefOp extends Operator {
    public static final UndefOp instance = new UndefOp();

    protected UndefOp() {
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
        dict.setValue(psDictionary.undef(key));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("undef");
    }
}
