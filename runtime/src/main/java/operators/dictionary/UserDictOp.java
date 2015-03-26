package operators.dictionary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class UserDictOp extends Operator {
    public static final UserDictOp instance = new UserDictOp();

    protected UserDictOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.pushToOperandStack(context.getUserDict());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("userdict");
    }
}
