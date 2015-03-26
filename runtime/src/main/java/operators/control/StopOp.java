package operators.control;

import procedures.Procedure;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class StopOp extends Operator {
    public static final StopOp instance = new StopOp();

    protected StopOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        Procedure procedure = context.popFromCallStack();
        if (procedure != null) {
            procedure.procBreak();
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("stop");
    }
}
