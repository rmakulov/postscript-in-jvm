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
public class IfElseOp extends Operator {
    public static final IfElseOp instance = new IfElseOp();

    protected IfElseOp() {
        super();
    }

    @Override
    public boolean interpret(PSObject obj) {
        if (runtime.getOperandStackSize() < 3) return true;
        PSObject proc2 = runtime.popFromOperandStack();
        PSObject proc1 = runtime.popFromOperandStack();
        PSObject bool = runtime.popFromOperandStack();
        if (wrongArgs(proc2, proc1, bool)) return true;

        PSBoolean cond = (PSBoolean) bool.getValue();
        return execBranch(proc1, proc2, cond.getFlag());
/*        if (cond.getFlag()) {
            return trueBranch(proc1);
        } else {
            return falseBranch(proc2);
        }*/
    }

    @Override
    public void interpret() {
        System.out.println("lol");
//        if (runtime.getOperandStackSize() < 3) return;
//        PSObject proc2 = runtime.popFromOperandStack();
//        PSObject proc1 = runtime.popFromOperandStack();
//        PSObject bool = runtime.popFromOperandStack();
//        if (wrongArgs(proc2, proc1, bool)) return;
//
//        PSBoolean cond = (PSBoolean) bool.getValue();
//        if (cond.getFlag()) {
//            trueBranch(proc1);
//        } else {
//            falseBranch(proc2);
//        }
    }

/*    private boolean falseBranch(PSObject proc) {
        if (runtime.isCompiling) {
            return proc.execute(0);
        } else {
            runtime.pushToCallStack(new ArrayProcedure("IfElse Procedure (false)", proc));
        }
        return true;
    }

    private boolean trueBranch(PSObject proc) {
        if (runtime.isCompiling) {
            return proc.execute(0);
        } else {
            runtime.pushToCallStack(new ArrayProcedure("IfElse Procedure (true)", proc));
        }
        return true;
    }*/

    private boolean execBranch(PSObject proc1, PSObject proc2, boolean condition) {
        String name = condition ? "true" : "false";
        PSObject proc = condition ? proc1 : proc2;
        runtime.pushToCallStack(new ArrayProcedure("IfElse Procedure (" + name + ")", proc));
        if (runtime.isCompiling) {
            boolean execute = proc.execute(0);
            runtime.popFromCallStack();
            return execute;
        } /*else {
            runtime.pushToCallStack(new ArrayProcedure("IfElse Procedure ("+name+")", proc));
        }*/
        return true;
    }

    private boolean wrongArgs(PSObject proc2, PSObject proc1, PSObject bool) {
        if (!proc1.isProc() || !proc2.isProc() || bool.getType() != Type.BOOLEAN) {
            fail();
            runtime.pushToOperandStack(bool);
            runtime.pushToOperandStack(proc1);
            runtime.pushToOperandStack(proc2);
            return true;
        }
        return false;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("ifelse");
    }
}
