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
        if (cond.getFlag()) {
            return trueBranch(proc1);
        } else {
            return falseBranch(proc2);
        }
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

    private boolean falseBranch(PSObject proc) {
        if (runtime.isCompiling && proc.isBytecode()) {
            return proc.execute(0);
        } else if (!runtime.isCompiling && proc.isProc()) {
            runtime.pushToCallStack(new ArrayProcedure("IfElse Procedure (false)", proc));
        } else {
            fail();
        }
        return true;
    }

    private boolean trueBranch(PSObject proc) {
        if (runtime.isCompiling && proc.isBytecode()) {
            return proc.execute(0);
        } else if (!runtime.isCompiling && proc.isProc()) {
            runtime.pushToCallStack(new ArrayProcedure("IfElse Procedure (true)", proc));
        } else {
            fail();
        }
        return true;
    }

    private boolean wrongArgs(PSObject proc2, PSObject proc1, PSObject bool) {
        if (!(proc1.isProc() || proc1.isBytecode())
                || !(proc2.isProc()
                || proc1.isBytecode())
                || bool.getType() != Type.BOOLEAN) {
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
