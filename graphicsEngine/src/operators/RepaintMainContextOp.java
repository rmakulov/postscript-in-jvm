package operators;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 01.04.15.
 */
public class RepaintMainContextOp extends Operator {

    public static final RepaintMainContextOp instance = new RepaintMainContextOp();

    protected RepaintMainContextOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        runtime.repaintMainContext();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("repaintMainContext");
    }
}

