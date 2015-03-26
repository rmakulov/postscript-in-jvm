package operators.painting;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class UfillOp extends Operator {
    public static final UfillOp instance = new UfillOp();

    protected UfillOp() {
        super();
    }

    @Override
    public void interpret(Context context) { // Fill current path with current color
        //todo
        //it is very difficult. it use userpath
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("ufill");
    }
}
