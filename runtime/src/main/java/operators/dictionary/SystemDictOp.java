package operators.dictionary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class SystemDictOp extends Operator {
    public static final SystemDictOp instance = new SystemDictOp();

    protected SystemDictOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.pushToOperandStack(runtime.getSystemDict());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("systemdict");
    }
}
