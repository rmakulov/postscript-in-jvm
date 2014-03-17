package operators.dictionary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 18.03.14.
 */
public class EndOp extends Operator {

    public static final EndOp instance = new EndOp();

    protected EndOp() {
        super();
    }

    @Override
    public void execute() {
        runtime.removeFromDictionaryStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("end");
    }
}
