package operators.virtualMemory;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class CurrentGlobalOp extends Operator {

    public static final CurrentGlobalOp instance = new CurrentGlobalOp();

    protected CurrentGlobalOp() {
        super();
    }

    @Override
    public void execute() {
        boolean b = runtime.currentGlobal();
        runtime.pushToOperandStack(new PSObject(PSBoolean.get(b)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("currentglobal");
    }
}
