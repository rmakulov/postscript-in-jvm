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
    public boolean interpret(PSObject obj) {
        if (runtime.getOperandStackSize() < 2) return true;
        PSObject proc = runtime.popFromOperandStack();
        PSObject bool = runtime.popFromOperandStack();
        if (wrongArgs(proc, bool)) return true;
        PSBoolean cond = (PSBoolean) bool.getValue();
        if (cond.getFlag()) {
            runtime.pushToCallStack(new ArrayProcedure("If", proc));
            if (runtime.isCompiling) {
                boolean execute = proc.execute(0);
                runtime.popFromCallStack();
                return execute;
            } /*else {
                runtime.pushToCallStack(new ArrayProcedure("If", proc));
            }*/
        }
        return true;
    }

    @Override
    public void interpret() {
        System.out.println("lol");
//        if (runtime.getOperandStackSize() < 2) return;
//        PSObject proc = runtime.popFromOperandStack();
//        PSObject bool = runtime.popFromOperandStack();
//        if (wrongArgs(proc, bool)) return;
//
//        PSBoolean cond = (PSBoolean) bool.getValue();
//        if (cond.getFlag()) {
//            if (runtime.isCompiling && proc.isBytecode()) {
//                proc.execute(0);
//            } else if (!runtime.isCompiling && proc.isProc()) {
//                runtime.pushToCallStack(new ArrayProcedure("If", proc));
//            } else {
//                fail();
//            }
//        }
    }

    private boolean wrongArgs(PSObject proc, PSObject bool) {
        if (!proc.isProc() || bool.getType() != Type.BOOLEAN) {
            fail();
            runtime.pushToOperandStack(bool);
            runtime.pushToOperandStack(proc);
            return true;
        }
        return false;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("if");
    }
}
