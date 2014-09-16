package operators.dictionary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class BeginOp extends Operator {

    public static final BeginOp instance = new BeginOp();

    protected BeginOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null || psObject.getType() != Type.DICTIONARY)
            return;
        runtime.pushToDictionaryStack(psObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("begin");
    }
}
