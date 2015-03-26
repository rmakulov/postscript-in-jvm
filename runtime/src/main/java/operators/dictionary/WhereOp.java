package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 25.03.14.
 */
public class WhereOp extends Operator {
    public static final WhereOp instance = new WhereOp();

    protected WhereOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) return;
        PSObject oKey = context.popFromOperandStack();
        PSObject dictObj = context.findDict(oKey);
        if (dictObj == null) {
            context.pushToOperandStack(new PSObject(PSBoolean.FALSE));
        } else {
            context.pushToOperandStack(dictObj);
            context.pushToOperandStack(new PSObject(PSBoolean.TRUE));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("where");
    }
}
