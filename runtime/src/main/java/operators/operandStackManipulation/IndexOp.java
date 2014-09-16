package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by user on 26.03.14.
 */
public class IndexOp extends Operator {
    public static final IndexOp instance = new IndexOp();

    protected IndexOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject oN = runtime.popFromOperandStack();
        if (!(oN.getType() == Type.INTEGER)) {
            runtime.pushToOperandStack(oN);
            return;
        }
        PSInteger psN = (PSInteger) oN.getValue();
        int n = psN.getIntValue();
        if (runtime.getOperandStackSize() <= n) {
            runtime.pushToOperandStack(oN);
            return;
        }
        PSObject[] arr = new PSObject[n + 1];
        for (int i = 0; i <= n; i++) {
            arr[i] = runtime.popFromOperandStack();
        }
        PSObject dupObj = new PSObject(arr[n].getDirectValue(), arr[n].getType(), arr[n].getAttribute());
        for (int i = n; i >= 0; i--) {
            runtime.pushToOperandStack(arr[i]);
        }
        runtime.pushToOperandStack(dupObj);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("index");
    }
}
