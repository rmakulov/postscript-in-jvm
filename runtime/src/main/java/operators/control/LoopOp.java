package operators.control;

import procedures.LoopProcedure;
import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class LoopOp extends Operator {
    public static final LoopOp instance = new LoopOp();

    protected LoopOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject proc = runtime.popFromOperandStack();
        if (proc.isProc() && !runtime.isCompiling) {
            runtime.pushToCallStack(new LoopProcedure(proc));
        } else if (proc.isBytecode() && runtime.isCompiling) {
            while (true) {
                if (!proc.execute(0)) break;
            }
        } else {
            fail();
            runtime.pushToOperandStack(proc);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("loop");
    }
}
