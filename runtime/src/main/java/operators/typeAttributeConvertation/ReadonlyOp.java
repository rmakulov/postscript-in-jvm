package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class ReadonlyOp extends Operator {

    public static final ReadonlyOp instance = new ReadonlyOp();

    protected ReadonlyOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        PSObject psObject = o.readOnly();
        if (psObject == null) {
            runtime.pushToOperandStack(o);
            return;
        }
        runtime.pushToOperandStack(psObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("readonly");
    }
}
