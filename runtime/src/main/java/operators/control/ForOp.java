package operators.control;


import procedures.ForProcedure;
import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;

public class ForOp extends Operator {
    public static final ForOp instance = new ForOp();

    protected ForOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 4) return;
        PSObject proc = runtime.popFromOperandStack();
        PSObject limit = runtime.popFromOperandStack();
        PSObject increment = runtime.popFromOperandStack();
        PSObject initial = runtime.popFromOperandStack();
        if (initial.isNumber() && increment.isNumber() && limit.isNumber() && proc.isProc()) {
            double start = ((PSNumber) (initial.getValue())).getRealValue();
            double end = ((PSNumber) (limit.getValue())).getRealValue();
            double incr = ((PSNumber) (increment.getValue())).getRealValue();
            runtime.pushToCallStack(new ForProcedure(start, incr, end, proc));
        } else {
            runtime.pushToOperandStack(initial);
            runtime.pushToOperandStack(increment);
            runtime.pushToOperandStack(limit);
            runtime.pushToOperandStack(proc);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("for");
    }
}
