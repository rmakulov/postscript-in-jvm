package operators.control;

import procedures.RepeatProcedure;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class RepeatOp extends Operator {
    public static final RepeatOp instance = new RepeatOp();

    protected RepeatOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject proc = context.popFromOperandStack();
        PSObject iObj = context.popFromOperandStack();
        if (wrongArgs(context, proc, iObj)) return;

        PSInteger psInteger = (PSInteger) iObj.getValue();
        int count = psInteger.getIntValue();
        if (wrongCount(context, proc, iObj, count)) return;

        if (runtime.isCompiling && proc.isBytecode()) {
            for (int i = 0; i < count; i++) {
                if (!proc.execute(context, 0)) break;
            }
        } else if (!runtime.isCompiling && proc.isProc()) {
            context.pushToCallStack(new RepeatProcedure(context, count, proc));
        } else {
            fail();
        }
    }

    private boolean wrongCount(Context context, PSObject proc, PSObject iObj, int count) {
        if (count < 0) {
            fail();
            context.pushToOperandStack(iObj);
            context.pushToOperandStack(proc);
            return true;
        }
        return false;
    }

    private boolean wrongArgs(Context context, PSObject proc, PSObject iObj) {
        if (!(proc.isProc() || proc.isBytecode()) || iObj.getType() != Type.INTEGER) {
            fail();
            context.pushToOperandStack(iObj);
            context.pushToOperandStack(proc);
            return true;
        }
        return false;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("repeat");
    }
}
