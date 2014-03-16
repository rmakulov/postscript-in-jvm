package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;


public class RcheckOp extends Operator {

    public static final RcheckOp instance = new RcheckOp();

    protected RcheckOp() {
        super();
    }

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
