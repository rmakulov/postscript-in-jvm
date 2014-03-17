package operators.control;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class RepeatOp extends Operator {
    public static final RepeatOp instance = new RepeatOp();

    protected RepeatOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject exec = runtime.popFromOperandStack();
        PSObject iObj = runtime.popFromOperandStack();
        if (!exec.isProc() || iObj.getType() != Type.INTEGER) {
            runtime.pushToOperandStack(iObj);
            runtime.pushToOperandStack(exec);
        }
        PSInteger psInteger = (PSInteger) iObj.getValue();
        int count = psInteger.getIntValue();
        if (count < 0) {
            runtime.pushToOperandStack(iObj);
            runtime.pushToOperandStack(exec);
        }
        PSArray arr = (PSArray) exec.getValue();
        for (int i = 0; i < count; i++) {
            for (PSObject o : arr.getArray()) {
                runtime.pushToOperandStack(o);
                ExecOp.instance.execute();
            }
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("repeat");
    }
}
