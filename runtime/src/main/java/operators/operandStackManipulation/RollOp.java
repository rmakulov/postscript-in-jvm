package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by user on 26.03.14.
 */
public class RollOp extends Operator {
    public static final RollOp instance = new RollOp();

    protected RollOp() {
        super();
    }

    @Override
    public void interpret() { //anyn-1 … any0 n j roll any (j-1) mod n … any0 anyn-1 … anyj mod n
        if (runtime.getOperandStackSize() < 2) return;
        PSObject oJ = runtime.popFromOperandStack();
        PSObject oN = runtime.popFromOperandStack();
        if (!(oJ.getType() == Type.INTEGER && oN.getType() == Type.INTEGER)) {
            runtime.pushToOperandStack(oN);
            runtime.pushToOperandStack(oJ);
            return;
        }
        PSInteger psN = (PSInteger) oN.getValue();
        PSInteger psJ = (PSInteger) oJ.getValue();
        int n = psN.getIntValue();
        int j = psJ.getIntValue();
        if (runtime.getOperandStackSize() < n) {
            runtime.pushToOperandStack(oN);
            runtime.pushToOperandStack(oJ);
            return;
        }

        j = j < 0 ? n - (Math.abs(j) % n) : j % n;

        PSObject[] arr = new PSObject[n];
        for (int i = 0; i < n; i++) {
            arr[i] = runtime.popFromOperandStack();
        }
        for (int i = (j - 1) % n; i >= 0; i--) {
            runtime.pushToOperandStack(arr[i]);
        }
        for (int i = n - 1; i >= j; i--) {
            runtime.pushToOperandStack(arr[i]);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("roll");
    }
}
