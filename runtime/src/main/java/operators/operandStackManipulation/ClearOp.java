package operators.operandStackManipulation;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class ClearOp extends Operator {
    @Override
    public void execute() {
        runtime.clear();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("clear");
    }
}
