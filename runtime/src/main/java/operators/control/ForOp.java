package operators.control;


import procedures.ForProcedure;
import procedures.IntForProcedure;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSNumber;
import psObjects.values.simple.numbers.PSReal;


public class ForOp extends Operator {
    public static final ForOp instance = new ForOp();

    protected ForOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 4) return;
        PSObject proc = runtime.popFromOperandStack();
        PSObject limit = runtime.popFromOperandStack();
        PSObject increment = runtime.popFromOperandStack();
        PSObject initial = runtime.popFromOperandStack();
        if (!proc.isProc()) {
            fail();
            return;
        }
        if (initial.getType().equals(Type.INTEGER) && increment.getType().equals(Type.INTEGER) && limit.isNumber()) {
            int start = ((PSInteger) (initial.getValue())).getIntValue();
            double end = ((PSNumber) (limit.getValue())).getRealValue();
            int incr = ((PSInteger) (increment.getValue())).getIntValue();
            intInterpret(proc, start, end, incr);
        } else if (initial.isNumber() && increment.isNumber() && limit.isNumber()) {
            double start = ((PSNumber) (initial.getValue())).getRealValue();
            double end = ((PSNumber) (limit.getValue())).getRealValue();
            double incr = ((PSNumber) (increment.getValue())).getRealValue();
            realInterpret(proc, start, end, incr);
        } else {
            fail();
            runtime.pushToOperandStack(initial);
            runtime.pushToOperandStack(increment);
            runtime.pushToOperandStack(limit);
            runtime.pushToOperandStack(proc);
        }
    }

    // real steps
    private void realInterpret(PSObject proc, double start, double end, double incr) {
        /* for both modes:
         for interpret - procedure is executed by execNext
         for compile - procedure only not to be removed by gc
        */
        runtime.pushToCallStack(new ForProcedure(start, incr, end, proc));
        if (runtime.isCompiling) {
            for (double i = start; i <= end; i += incr) {
                runtime.pushToOperandStack(new PSObject(new PSReal(i)));
                if (!proc.execute(0)) break;
            }
            runtime.popFromCallStack();
        } /*else {
            runtime.pushToCallStack(new ForProcedure(start, incr, end, proc));
        }*/
    }

    // integer step
    private void intInterpret(PSObject proc, int start, double end, int incr) {
        /* for both modes:
         for interpret - procedure is executed by execNext
         for compile - procedure only not to be removed by gc
        */
        runtime.pushToCallStack(new IntForProcedure(start, incr, end, proc));
        if (runtime.isCompiling) {
            for (int i = start; i <= end; i += incr) {
                runtime.pushToOperandStack(new PSObject(new PSInteger(i)));
                boolean interrupted = !proc.execute(0);
                if (interrupted) break;
            }
            runtime.popFromCallStack();
        } /*else {
            runtime.pushToCallStack(new IntForProcedure(start, incr, end, proc));
        }*/
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("for");
    }
}
