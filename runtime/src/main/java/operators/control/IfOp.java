package operators.control;

import procedures.ArrayProcedure;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class IfOp extends Operator {
    public static final IfOp instance = new IfOp();

    protected IfOp() {
        super();
    }

    @Override
    public boolean interpret(Context context, PSObject obj) {
        if (context.getOperandStackSize() < 2) return true;
        PSObject proc = context.popFromOperandStack();
        PSObject bool = context.popFromOperandStack();
        if (wrongArgs(context, proc, bool)) return true;

        PSBoolean cond = (PSBoolean) bool.getValue();
        if (cond.getFlag()) {
            if (runtime.isCompiling && proc.isBytecode()) {
                return proc.execute(context, 0);
            } else if (!runtime.isCompiling && proc.isProc()) {
                context.pushToCallStack(new ArrayProcedure(context, "If", proc));
            } else {
                fail();
            }
        }
        return true;
    }

    @Override
    public void interpret(Context context) {
//        if (runtime.getOperandStackSize() < 2) return;
//        PSObject proc = context.popFromOperandStack();
//        PSObject bool = context.popFromOperandStack();
//        if (wrongArgs(proc, bool)) return;
//
//        PSBoolean cond = (PSBoolean) bool.getValue();
//        if (cond.getFlag()) {
//            if (runtime.isCompiling && proc.isBytecode()) {
//                proc.execute(0);
//            } else if (!runtime.isCompiling && proc.isProc()) {
//                context.pushToCallStack(new ArrayProcedure("If", proc));
//            } else {
//                fail();
//            }
//        }
    }

    private boolean wrongArgs(Context context, PSObject proc, PSObject bool) {
        if (!(proc.isProc() || proc.isBytecode()) || bool.getType() != Type.BOOLEAN) {
            fail();
            context.pushToOperandStack(bool);
            context.pushToOperandStack(proc);
            return true;
        }
        return false;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("if");
    }
}
