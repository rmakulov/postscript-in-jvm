package operators.arithmetic;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class RrandOp extends Operator {
    public static final RrandOp instance = new RrandOp();

    protected RrandOp() {
        super();
    }

    @Override
    public void interpret(Context context) {

        RandOp.dropRandom();
        RandOp.instance.interpret(context);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rrand");
    }
}
