package operators.virtualMemory;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class GcheckOp extends Operator {
    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        runtime.pushToOperandStack(new PSObject(PSBoolean.get(o.gcheck())));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("gcheck");
    }
}
