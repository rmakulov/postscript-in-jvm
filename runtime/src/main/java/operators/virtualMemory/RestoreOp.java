package operators.virtualMemory;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class RestoreOp extends Operator {

    public static final RestoreOp instance = new RestoreOp();

    protected RestoreOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.restore();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("restore");
    }
}
