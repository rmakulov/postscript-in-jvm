package operators.control;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class CountExecStackOp extends Operator {
    public static final CountExecStackOp instance = new CountExecStackOp();

    protected CountExecStackOp() {
        super();
    }

    @Override
    public void execute() {
        runtime.pushToOperandStack(new PSObject(new PSInteger(runtime.getCallStackSize())));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("countexecstack");
    }
}
