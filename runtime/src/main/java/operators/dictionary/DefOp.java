package operators.dictionary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class DefOp extends Operator {
    public static final DefOp instance = new DefOp();

    protected DefOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject psValue = context.popFromOperandStack();
        PSObject psKey = context.popFromOperandStack();
        if (!psKey.isDictKey()) {
            context.pushToOperandStack(psKey);
            context.pushToOperandStack(psValue);
            return;
        }
        PSObject dictObj = context.currentDict();
        PSDictionary dict = (PSDictionary) dictObj.getValue();

        PSDictionary newDict = dict.put(psKey, psValue);
        dictObj.setValue(newDict);
        if (psKey.getType() == Type.NAME) {
            String name = ((PSName) psKey.getValue()).getStrValue();
            context.updateNameVersions(name);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        PSName psName = new PSName("def");
        return psName;
    }
}
