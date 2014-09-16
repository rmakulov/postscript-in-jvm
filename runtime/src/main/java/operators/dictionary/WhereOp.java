package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

/**
 * Created by user on 25.03.14.
 */
public class WhereOp extends Operator {
    public static final WhereOp instance = new WhereOp();

    protected WhereOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject oKey = runtime.popFromOperandStack();
        PSObject dictObj = runtime.findDict(oKey);
        if (dictObj == null) {
            runtime.pushToOperandStack(new PSObject(PSBoolean.FALSE));
        } else {
            runtime.pushToOperandStack(dictObj);
            runtime.pushToOperandStack(new PSObject(PSBoolean.TRUE));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("where");
    }
}
