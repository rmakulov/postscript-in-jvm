package operators.virtualMemory;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class SetGlobalOp extends Operator {

    public static final SetGlobalOp instance = new SetGlobalOp();

    protected SetGlobalOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        if (o.getType() != Type.BOOLEAN) {
            context.pushToOperandStack(o);
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
