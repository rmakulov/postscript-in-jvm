package operators.miscellaneous;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by User on 6/12/2014.
 */
public class LanguageLevelOp extends Operator {

    public static final LanguageLevelOp instance = new LanguageLevelOp();

    protected LanguageLevelOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.pushToOperandStack(new PSObject(new PSInteger(3)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("languagelevel");
    }
}

