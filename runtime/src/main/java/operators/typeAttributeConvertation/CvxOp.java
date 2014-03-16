package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class CvxOp extends Operator {
    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        runtime.pushToOperandStack(o.cvx());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvx");
    }
}
