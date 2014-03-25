package operators.control;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class IfElseOp extends Operator {
    public static final IfElseOp instance = new IfElseOp();

    protected IfElseOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 3) return;
        PSObject exec2 = runtime.popFromOperandStack();
        PSObject exec1 = runtime.popFromOperandStack();
        PSObject bool = runtime.popFromOperandStack();
        if (!exec1.isProc() || !exec2.isProc() || bool.getType() != Type.BOOLEAN) {
            runtime.pushToOperandStack(bool);
            runtime.pushToOperandStack(exec1);
            runtime.pushToOperandStack(exec2);
        }
        PSBoolean cond = (PSBoolean) bool.getValue();
        if (cond.getFlag()) {
            runtime.pushToOperandStack(exec1);
            ExecOp.instance.execute();
        } else {
            runtime.pushToOperandStack(exec2);
            ExecOp.instance.execute();
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("ifelse");
    }
}
