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
public class IfElseOp extends Operator {
    public static final IfElseOp instance = new IfElseOp();

    protected IfElseOp() {
        super();
    }

    @Override
    public boolean interpret(Context context, PSObject obj) {
        if (context.getOperandStackSize() < 3) return true;
        PSObject proc2 = context.popFromOperandStack();
        PSObject proc1 = context.popFromOperandStack();
        PSObject bool = context.popFromOperandStack();
        if (wrongArgs(context, proc2, proc1, bool)) return true;

        PSBoolean cond = (PSBoolean) bool.getValue();
        if (cond.getFlag()) {
            return trueBranch(context, proc1);
        } else {
            return falseBranch(context, proc2);
        }
    }

    @Override
    public void interpret(Context context) {
        System.out.println("lol");
//        if (runtime.getOperandStackSize() < 3) return;
//        PSObject proc2 = context.popFromOperandStack();
//        PSObject proc1 = context.popFromOperandStack();
//        PSObject bool = context.popFromOperandStack();
//        if (wrongArgs(proc2, proc1, bool)) return;
//
//        PSBoolean cond = (PSBoolean) bool.getValue();
//        if (cond.getFlag()) {
//            trueBranch(proc1);
//        } else {
//            falseBranch(proc2);
//        }
    }

    private boolean falseBranch(Context context, PSObject proc) {
        if (runtime.isCompiling && proc.isBytecode()) {
            return proc.execute(context, 0);
        } else if (!runtime.isCompiling && proc.isProc()) {
            context.pushToCallStack(new ArrayProcedure(context, "IfElse Procedure (false)", proc));
        } else {
            fail();
        }
        return true;
    }

    private boolean trueBranch(Context context, PSObject proc) {
        if (runtime.isCompiling && proc.isBytecode()) {
            return proc.execute(context, 0);
        } else if (!runtime.isCompiling && proc.isProc()) {
            context.pushToCallStack(new ArrayProcedure(context, "IfElse Procedure (true)", proc));
        } else {
            fail();
        }
        return true;
    }

    private boolean wrongArgs(Context context, PSObject proc2, PSObject proc1, PSObject bool) {
        if (!(proc1.isProc() || proc1.isBytecode())
                || !(proc2.isProc()
                || proc1.isBytecode())
                || bool.getType() != Type.BOOLEAN) {
            fail();
            context.pushToOperandStack(bool);
            context.pushToOperandStack(proc1);
            context.pushToOperandStack(proc2);
            return true;
        }
        return false;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("ifelse");
    }
}
