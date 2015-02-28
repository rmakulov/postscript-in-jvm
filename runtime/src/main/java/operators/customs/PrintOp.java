package operators.customs;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 27.02.15.
 */
public class PrintOp extends Operator {

    public static final PrintOp instance = new PrintOp();

    protected PrintOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 1) {
            fail();
        }
        PSObject obj = runtime.popFromOperandStack();
        System.out.println(obj);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("print");
    }
}
