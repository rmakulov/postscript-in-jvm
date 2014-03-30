package operators.array;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class AStoreOp extends Operator {

    public static final AStoreOp instance = new AStoreOp();

    protected AStoreOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject o = runtime.popFromOperandStack();
        if (o.getType() != Type.ARRAY) {
            runtime.pushToOperandStack(o);
            return;
        }
        PSArray psArray = (PSArray) o.getValue();
        int n = psArray.getArray().length;
        if (runtime.getOperandStackSize() < n) {
            runtime.pushToOperandStack(o);
            return;
        }
        PSObject[] array = new PSObject[n];
        for (int i = 0; i < n; i++) {
            array[n - i - 1] = runtime.popFromOperandStack();
        }
        PSArray newPSArray = new PSArray(array);
        o.setValue(newPSArray);
        runtime.pushToOperandStack(o);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("astore");
    }
}
