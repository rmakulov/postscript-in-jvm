package operators.dictionary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class UserDictOp extends Operator {
    public static final UserDictOp instance = new UserDictOp();

    protected UserDictOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.pushToOperandStack(runtime.getUserDict());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("userdict");
    }
}
