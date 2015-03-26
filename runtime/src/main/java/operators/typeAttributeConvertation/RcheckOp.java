package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;


public class RcheckOp extends Operator {

    public static final RcheckOp instance = new RcheckOp();

    protected RcheckOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        context.pushToOperandStack(new PSObject(PSBoolean.get(o.rcheck())));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rcheck");
    }
}
