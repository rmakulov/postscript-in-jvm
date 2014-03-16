package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class AloadOp extends Operator {

    @Override
    public void execute() {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null)
            return;
        if (psObject.getType() == Type.ARRAY || psObject.getType() == Type.PACKEDARRAY) {
            PSObject[] array = ((PSArray) psObject.getValue()).getArray();
            for (PSObject object : array) {
                runtime.pushToOperandStack(object);
            }
            runtime.pushToOperandStack(psObject);
        } else {
            runtime.pushToOperandStack(psObject);
            return;
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("aload");
    }
}
