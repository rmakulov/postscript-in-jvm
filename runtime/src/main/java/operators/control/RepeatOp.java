package operators.control;

import procedures.RepeatProcedure;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class RepeatOp extends Operator {
    public static final RepeatOp instance = new RepeatOp();

    protected RepeatOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject proc = runtime.popFromOperandStack();
        PSObject iObj = runtime.popFromOperandStack();
        if (wrongArgs(proc, iObj)) return;

        PSInteger psInteger = (PSInteger) iObj.getValue();
        int count = psInteger.getIntValue();
        if (wrongCount(proc, iObj, count)) return;

        if (runtime.isCompiling) {
            for (int i = 0; i < count; i++) {
                if (!proc.execute(0)) break;
            }
        } else {
            runtime.pushToCallStack(new RepeatProcedure(count, proc));
        }
    }

    private boolean wrongCount(PSObject proc, PSObject iObj, int count) {
        if (count < 0) {
            fail();
            runtime.pushToOperandStack(iObj);
            runtime.pushToOperandStack(proc);
            return true;
        }
        return false;
    }

    private boolean wrongArgs(PSObject proc, PSObject iObj) {
        if (!(proc.isProc() || iObj.getType() != Type.INTEGER)) {
            fail();
            runtime.pushToOperandStack(iObj);
            runtime.pushToOperandStack(proc);
            return true;
        }
        return false;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("repeat");
    }
}
