package operators;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 27.02.15.
 */
public class PrintOp extends Operator {

    public static final PrintOp instance = new PrintOp();

    protected PrintOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) {
            fail();
        }
        PSObject obj = context.popFromOperandStack();
//        System.out.println(obj);
        System.out.println(obj.getValue().toString());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("print");
    }
}
