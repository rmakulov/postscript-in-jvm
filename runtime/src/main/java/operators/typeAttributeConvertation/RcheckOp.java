package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class RcheckOp extends Operator {
    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        runtime.pushToOperandStack(new PSObject(PSBoolean.get(o.rcheck())));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rcheck");
    }
}
