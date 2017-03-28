package operators.virtualMemory;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class CurrentGlobalOp extends Operator {

    public static final CurrentGlobalOp instance = new CurrentGlobalOp();

    protected CurrentGlobalOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        boolean b = runtime.currentGlobal();
        context.pushToOperandStack(new PSObject(PSBoolean.get(b)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("currentglobal");
    }
}
