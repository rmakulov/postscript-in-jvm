package operators.virtualMemory;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class GcheckOp extends Operator {

    public static final GcheckOp instance = new GcheckOp();

    protected GcheckOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        context.pushToOperandStack(new PSObject(PSBoolean.get(o.gcheck())));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("gcheck");
    }
}
