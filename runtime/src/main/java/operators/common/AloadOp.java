package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class AloadOp extends Operator {

    public static final AloadOp instance = new AloadOp();

    protected AloadOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null)
            return;
        if (psObject.getType() == Type.ARRAY || psObject.getType() == Type.PACKEDARRAY) {
            PSObject[] array = ((PSArray) psObject.getValue()).getArray();
//            System.out.print("Aload: ");
            for (PSObject object : array) {
                runtime.pushToOperandStack(object);
//                System.out.print(object.getDirectValue() + " ");
            }
//            System.out.println();
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
