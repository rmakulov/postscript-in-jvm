package operators.arithmetic;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class RrandOp extends Operator {
    public static final RrandOp instance = new RrandOp();

    protected RrandOp() {
        super();
    }

    @Override
    public void execute() {

        RandOp.dropRandom();
        RandOp.instance.execute();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rrand");
    }
}
