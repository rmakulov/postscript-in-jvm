package operators.control;

import procedures.ArrayProcedure;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class IfOp extends Operator {
    public static final IfOp instance = new IfOp();

    protected IfOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject exec = runtime.popFromOperandStack();
        PSObject bool = runtime.popFromOperandStack();
        if (!exec.isProc() || bool.getType() != Type.BOOLEAN) {
            runtime.pushToOperandStack(bool);
            runtime.pushToOperandStack(exec);
            return;
        }
        PSBoolean cond = (PSBoolean) bool.getValue();
        if (cond.getFlag()) {
            runtime.pushToCallStack(new ArrayProcedure("If Procedure", exec));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("if");
    }
}
