package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;

public class WcheckOp extends Operator {

    public static final WcheckOp instance = new WcheckOp();

    protected WcheckOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        runtime.pushToOperandStack(new PSObject(PSBoolean.get(o.wcheck())));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("wcheck");
    }
}

