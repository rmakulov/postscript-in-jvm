package operators.dictionary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 18.03.14.
 */
public class EndOp extends Operator {

    public static final EndOp instance = new EndOp();

    protected EndOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.removeFromDictionaryStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("end");
    }
}
