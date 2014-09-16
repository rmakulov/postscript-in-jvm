package operators.control;

import procedures.Procedure;
import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class ExitOp extends Operator {
    public static final ExitOp instance = new ExitOp();

    protected ExitOp() {
        super();
    }

    @Override
    public boolean interpret(PSObject obj) {
        if (runtime.isCompiling) {
            return false;
        } else {
            interpret();
            return true;
        }
    }

    @Override
    public void interpret() {
        Procedure procedure;
        while ((procedure = runtime.peekFromCallStack()) != null && !procedure.isExitable()) {
            procedure.procBreak();
            runtime.popFromCallStack();
        }
        if (procedure == null) {
            return;
        }
        procedure.procBreak();
        runtime.popFromCallStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exit");
    }


}
