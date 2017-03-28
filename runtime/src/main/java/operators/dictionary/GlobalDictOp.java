package operators.dictionary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class GlobalDictOp extends Operator {
    public static final GlobalDictOp instance = new GlobalDictOp();

    protected GlobalDictOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.pushToOperandStack(context.getGlobalDict());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("globaldict");
    }
}
