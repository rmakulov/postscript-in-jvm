package operators;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by stack on 26.03.17.
 */
public class UserPrintOp extends Operator {
    public static final UserPrintOp instance = new UserPrintOp();

    protected UserPrintOp() {
        super();
    }

    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) {
            fail();
        }
        PSObject obj = context.popFromOperandStack();
        System.out.println(obj.getValue().toStringView(obj));
    }

    public PSName getDefaultKeyName() {
        return new PSName("userprint");

    }
}
