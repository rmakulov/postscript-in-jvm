package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class ExecuteonlyOp extends Operator {

    public static final ExecuteonlyOp instance = new ExecuteonlyOp();

    protected ExecuteonlyOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        PSObject psObject = o.executeonly();
        if (psObject == null) {
            runtime.pushToOperandStack(o);
            return;
        }
        runtime.pushToOperandStack(psObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("executeonly");
    }
}
