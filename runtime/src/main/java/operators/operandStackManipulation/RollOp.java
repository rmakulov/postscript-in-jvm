package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by user on 26.03.14.
 */
public class RollOp extends Operator {
    public static final RollOp instance = new RollOp();

    protected RollOp() {
        super();
    }

    @Override
    public void interpret(Context context) { //anyn-1 … any0 n j roll any (j-1) mod n … any0 anyn-1 … anyj mod n
        if (context.getOperandStackSize() < 2) return;
        PSObject oJ = context.popFromOperandStack();
        PSObject oN = context.popFromOperandStack();
        if (!(oJ.getType() == Type.INTEGER && oN.getType() == Type.INTEGER)) {
            context.pushToOperandStack(oN);
            context.pushToOperandStack(oJ);
            fail();
            return;
        }
        PSInteger psN = (PSInteger) oN.getValue();
        PSInteger psJ = (PSInteger) oJ.getValue();
        int n = psN.getIntValue();
        int j = psJ.getIntValue();
        if (context.getOperandStackSize() < n) {
            context.pushToOperandStack(oN);
            context.pushToOperandStack(oJ);
            return;
        }

        j = j < 0 ? n - (Math.abs(j) % n) : j % n;

        PSObject[] arr = new PSObject[n];
        for (int i = 0; i < n; i++) {
            arr[i] = context.popFromOperandStack();
        }
        for (int i = (j - 1) % n; i >= 0; i--) {
            context.pushToOperandStack(arr[i]);
        }
        for (int i = n - 1; i >= j; i--) {
            context.pushToOperandStack(arr[i]);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("roll");
    }
}
