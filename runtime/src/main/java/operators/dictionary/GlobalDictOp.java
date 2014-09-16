package operators.dictionary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class GlobalDictOp extends Operator {
    public static final GlobalDictOp instance = new GlobalDictOp();

    protected GlobalDictOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.pushToOperandStack(runtime.getGlobalDict());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("globaldict");
    }
}
