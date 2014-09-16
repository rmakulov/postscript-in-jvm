package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class CountDictStackOp extends Operator {
    public static final CountDictStackOp instance = new CountDictStackOp();

    protected CountDictStackOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.pushToOperandStack(new PSObject(new PSInteger(runtime.getDictionaryStackSize())));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("countdictstack");
    }
}
