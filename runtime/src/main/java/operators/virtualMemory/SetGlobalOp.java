package operators.virtualMemory;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class SetGlobalOp extends Operator {
    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o ==null) return;
        if (o.getType() != Type.BOOLEAN) {
            runtime.pushToOperandStack(o);
            return;
        }
        PSBoolean b = (PSBoolean) o.getValue();
        runtime.setGlobal(b.getFlag());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setglobal");
    }
}
