package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class NoAccessOp extends Operator {

    public static final NoAccessOp instance = new NoAccessOp();

    protected NoAccessOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        PSObject psObject = o.noaccess();
        if (psObject == null) {
            runtime.pushToOperandStack(o);
            return;
        }
        runtime.pushToOperandStack(psObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("noaccess");
    }
}
