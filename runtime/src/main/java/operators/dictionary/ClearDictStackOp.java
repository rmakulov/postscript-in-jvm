package operators.dictionary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Rustam Makulov on 17.03.14.
 */
public class ClearDictStackOp extends Operator {

    public static final ClearDictStackOp instance = new ClearDictStackOp();

    protected ClearDictStackOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.clearDictionaryStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cleardictstack");
    }
}

