package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class LoadOp extends Operator {
    public static final LoadOp instance = new LoadOp();

    protected LoadOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject key = runtime.popFromOperandStack();
        if (!key.isDictKey()) {
            runtime.pushToOperandStack(key);
            return;
        }
        runtime.pushToOperandStack(runtime.findValue(key));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("load");
    }
}
