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
import runtime.Context;


public class ForOp extends Operator {
    public static final ForOp instance = new ForOp();

    protected ForOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 4) return;
        PSObject proc = context.popFromOperandStack();
        PSObject limit = context.popFromOperandStack();
        PSObject increment = context.popFromOperandStack();
        PSObject initial = context.popFromOperandStack();
        if (initial.getType().equals(Type.INTEGER) && increment.getType().equals(Type.INTEGER) && limit.isNumber()) {
            int start = ((PSInteger) (initial.getValue())).getIntValue();
            double end = ((PSNumber) (limit.getValue())).getRealValue();
            int incr = ((PSInteger) (increment.getValue())).getIntValue();
            intInterpret(context, proc, start, end, incr);
        } else if (initial.isNumber() && increment.isNumber() && limit.isNumber()) {
            double start = ((PSNumber) (initial.getValue())).getRealValue();
            double end = ((PSNumber) (limit.getValue())).getRealValue();
            double incr = ((PSNumber) (increment.getValue())).getRealValue();
            realInterpret(context, proc, start, end, incr);
        } else {
            fail();
            context.pushToOperandStack(initial);
            context.pushToOperandStack(increment);
            context.pushToOperandStack(limit);
            context.pushToOperandStack(proc);
        }
    }


    private void realInterpret(Context context, PSObject proc, double start, double end, double incr) {
        if (!runtime.isCompiling && proc.isProc()) {
            context.pushToCallStack(new ForProcedure(context, start, incr, end, proc));
        } else if (runtime.isCompiling && proc.isBytecode()) {
            double signum = Math.signum(incr);
            for (double i = start; signum > 0 ? i <= end : i >= end; i += incr) {
                context.pushToOperandStack(new PSObject(new PSReal(i)));
                if (!proc.execute(context, 0)) break;
            }
        } else {
            fail();
        }
    }

    private void intInterpret(Context context, PSObject proc, int start, double end, int incr) {
        if (!runtime.isCompiling && proc.isProc()) {
            context.pushToCallStack(new IntForProcedure(context, start, incr, end, proc));
        } else if (runtime.isCompiling && proc.isBytecode()) {
            float signum = Math.signum(incr);
            /*(i <= end) && (signum >0) || (i >= end)  && (signum <0)*/
            for (int i = start; signum > 0 ? i <= end : i >= end; i += incr) {
                context.pushToOperandStack(new PSObject(new PSInteger(i)));
                if (!proc.execute(context, 0)) break;
            }
        } else {
            fail();
        }
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("for");
    }
}
