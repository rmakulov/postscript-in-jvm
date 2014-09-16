package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class DefOp extends Operator {
    public static final DefOp instance = new DefOp();

    protected DefOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject psValue = runtime.popFromOperandStack();
        PSObject psKey = runtime.popFromOperandStack();
        if (!psKey.isDictKey()) {
            runtime.pushToOperandStack(psKey);
            runtime.pushToOperandStack(psValue);
            return;
        }
        PSObject dictObj = runtime.currentDict();
        PSDictionary dict = (PSDictionary) dictObj.getValue();

        PSDictionary newDict = dict.put(psKey, psValue);
        dictObj.setValue(newDict);
    }

    @Override
    public PSName getDefaultKeyName() {
        PSName psName = new PSName("def");
        return psName;
    }
}
