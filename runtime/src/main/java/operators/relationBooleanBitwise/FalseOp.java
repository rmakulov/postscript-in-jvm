package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class FalseOp extends Operator {
    @Override
    public void execute() {
        runtime.pushToOperandStack(new PSObject(PSBoolean.FALSE));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("false");
    }
}
