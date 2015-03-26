package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

public class NoaccessOp extends Operator {

    public static final NoaccessOp instance = new NoaccessOp();

    protected NoaccessOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        PSObject psObject = o.noaccess();
        if (psObject == null) {
            context.pushToOperandStack(o);
            return;
        }
        context.pushToOperandStack(psObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("noaccess");
    }
}
