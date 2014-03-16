package operators.virtualMemory;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class SaveOp extends Operator {
    @Override
    public void execute() {
           runtime.save();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("save");
    }
}
