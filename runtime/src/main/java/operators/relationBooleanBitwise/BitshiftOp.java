package operators.relationBooleanBitwise;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class BitshiftOp extends Operator {
    @Override
    public void execute() {

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("bitshift");
    }
}
