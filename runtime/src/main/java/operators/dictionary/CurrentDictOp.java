package operators.dictionary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class CurrentDictOp extends Operator {
    public static final CurrentDictOp instance = new CurrentDictOp();

    protected CurrentDictOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.pushToOperandStack(context.peekFromDictionaryStack());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("currentdict");
    }
}
