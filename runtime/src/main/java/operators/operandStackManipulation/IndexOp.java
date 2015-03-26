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
public class IndexOp extends Operator {
    public static final IndexOp instance = new IndexOp();

    protected IndexOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) return;
        PSObject oN = context.popFromOperandStack();
        if (!(oN.getType() == Type.INTEGER)) {
            context.pushToOperandStack(oN);
            return;
        }
        PSInteger psN = (PSInteger) oN.getValue();
        int n = psN.getIntValue();
        if (context.getOperandStackSize() <= n) {
            context.pushToOperandStack(oN);
            return;
        }
        PSObject[] arr = new PSObject[n + 1];
        for (int i = 0; i <= n; i++) {
            arr[i] = context.popFromOperandStack();
        }
        PSObject dupObj = new PSObject(arr[n].getDirectValue(), arr[n].getType(), arr[n].getAttribute());
        for (int i = n; i >= 0; i--) {
            context.pushToOperandStack(arr[i]);
        }
        context.pushToOperandStack(dupObj);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("index");
    }
}
