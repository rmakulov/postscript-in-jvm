package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

public class ReadonlyOp extends Operator {

    public static final ReadonlyOp instance = new ReadonlyOp();

    protected ReadonlyOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        PSObject psObject = o.readOnly();
        if (psObject == null) {
            context.pushToOperandStack(o);
            return;
        }
        context.pushToOperandStack(psObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("readonly");
    }
}
