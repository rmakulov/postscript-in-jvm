package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;

public class WcheckOp extends Operator {

    public static final WcheckOp instance = new WcheckOp();

    protected WcheckOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        context.pushToOperandStack(new PSObject(PSBoolean.get(o.wcheck())));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("wcheck");
    }
}

