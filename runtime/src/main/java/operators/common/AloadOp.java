package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

public class AloadOp extends Operator {

    public static final AloadOp instance = new AloadOp();

    protected AloadOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject psObject = context.popFromOperandStack();
        if (psObject == null)
            return;
        Type type = psObject.getType();
        if (type == Type.ARRAY || type == Type.PACKEDARRAY) {
            PSObject[] array = ((PSArray) psObject.getValue()).getArray();
//            System.out.print("Aload: ");
            for (PSObject object : array) {
                context.pushToOperandStack(object);
//                System.out.print(object.getDirectValue() + " ");
            }
//            System.out.println();
            context.pushToOperandStack(psObject);
//        } else if (psObject.isBytecode()){

        } else if (psObject.isBytecode() && runtime.isCompiling) {
            context.setALoading(true);
            psObject.getValue().interpret(context, psObject);
            context.setALoading(false);
            context.pushToOperandStack(psObject);

        } else {
            context.pushToOperandStack(psObject);
            fail();
            return;
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("aload");
    }
}
