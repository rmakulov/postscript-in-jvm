package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class LoadOp extends Operator {
    public static final LoadOp instance = new LoadOp();

    protected LoadOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) return;
        PSObject key = context.popFromOperandStack();
        if (!key.isDictKey()) {
            context.pushToOperandStack(key);
            return;
        }
        context.pushToOperandStack(context.findValue(key));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("load");
    }
}
