package operators.dictionary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

public class BeginOp extends Operator {

    public static final BeginOp instance = new BeginOp();

    protected BeginOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject psObject = context.popFromOperandStack();
        if (psObject == null || psObject.getType() != Type.DICTIONARY) {
            fail();
            return;
        }
        context.pushToDictionaryStack(psObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("begin");
    }
}
