package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class TrueOp extends Operator {

    public static final TrueOp instance = new TrueOp();

    protected TrueOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.pushToOperandStack(new PSObject(PSBoolean.TRUE));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("true");
    }
}
