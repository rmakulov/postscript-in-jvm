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
        /* for both modes:
         for interpret - procedure is executed by execNext
         for compile - procedure only not to be removed by gc
        */
        if (runtime.getOperandStackSize() < 1) return;
        PSObject proc = runtime.popFromOperandStack();
        runtime.pushToCallStack(new LoopProcedure(proc));
        if (wrongArgs(proc)) return;

        if (runtime.isCompiling) {
            while (true) {
                if (!proc.execute(0)) break;
            }
            runtime.popFromCallStack();
        } /*else {
            runtime.pushToCallStack(new LoopProcedure(proc));
        }*/
    }

    private boolean wrongArgs(PSObject proc) {
        if (!proc.isProc()) {
            fail();
            runtime.pushToOperandStack(proc);
            return true;
        }
        return false;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("loop");
    }
}
