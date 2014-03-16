package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class DupOp extends Operator {
    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) {
            return;
        }
        PSObject dupObj = new PSObject(o.getDirectValue(),o.getType(),o.getAttribute());
        runtime.pushToOperandStack(o);
        runtime.pushToOperandStack(dupObj);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("dup");
    }
}