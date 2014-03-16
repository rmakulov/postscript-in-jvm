package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class CvnOp extends Operator {
    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        PSObject psObject = o.cvn();
        if (psObject == null) {
            runtime.pushToOperandStack(o);
            return;
        }
        runtime.pushToOperandStack(psObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvn");
    }
}
