package operators.control;

import procedures.LoopProcedure;
import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class LoopOp extends Operator {
    public static final LoopOp instance = new LoopOp();

    protected LoopOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) return;
        PSObject proc = context.popFromOperandStack();
        if (proc.isProc() && !runtime.isCompiling) {
            context.pushToCallStack(new LoopProcedure(context, proc));
        } else if (proc.isBytecode() && runtime.isCompiling) {
            while (true) {
                if (!proc.execute(context, 0)) break;
            }
        } else {
            fail();
            context.pushToOperandStack(proc);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("loop");
    }
}
