package operators.virtualMemory;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class SaveOp extends Operator {

    public static final SaveOp instance = new SaveOp();

    protected SaveOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        runtime.save(context);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("save");
    }
}
