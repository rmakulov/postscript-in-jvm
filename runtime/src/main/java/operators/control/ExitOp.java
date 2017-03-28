package operators.control;

import procedures.Procedure;
import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class ExitOp extends Operator {
    public static final ExitOp instance = new ExitOp();

    protected ExitOp() {
        super();
    }

    @Override
    public boolean interpret(Context context, PSObject obj) {
        if (runtime.isCompiling) {
            return false;
        } else {
            interpret(context);
            return true;
        }
    }

    @Override
    public void interpret(Context context) {
        Procedure procedure;
        while ((procedure = context.peekFromCallStack()) != null && !procedure.isExitable()) {
            procedure.procBreak();
            context.popFromCallStack();
        }
        if (procedure == null) {
            return;
        }
        procedure.procBreak();
        context.popFromCallStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exit");
    }


}
