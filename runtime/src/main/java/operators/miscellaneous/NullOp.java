package operators.miscellaneous;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.PSNull;

/**
 * Created by User on 21/10/2014.
 */
public class NullOp extends Operator {

    public static final NullOp instance = new NullOp();

    protected NullOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.pushToOperandStack(new PSObject(PSNull.NULL));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("null");
    }
}

