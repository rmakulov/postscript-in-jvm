package operators.customs;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 5/8/15.
 */
public class RepaintOp extends Operator {
    public static final RepaintOp instance = new RepaintOp();

    protected RepaintOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        runtime.getWindowManager().repaint();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("repaint");
    }
}
