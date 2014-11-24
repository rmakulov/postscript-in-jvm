package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;

public class AloadOp extends Operator {

    public static final AloadOp instance = new AloadOp();

    protected AloadOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null)
            return;
        Type type = psObject.getType();
        if (type == Type.ARRAY || type == Type.PACKEDARRAY) {
            PSObject[] array = ((PSArray) psObject.getValue()).getArray();
            for (PSObject object : array) {
                if (object.getValue().equals(PSMark.CLOSE_CURLY_BRACE)) {
                    object.interpret(0);
                } else {
                    runtime.pushToOperandStack(object);
                }
            }
            runtime.pushToOperandStack(psObject);
//        } else if (psObject.isBytecode()){
//            psObject.interpret(0);
//        } else if (psObject.isBytecode() && runtime.isCompiling) {
//            runtime.setALoading(true);
//            psObject.getValue().interpret(psObject);
////            psObject.interpret(0);
//            runtime.setALoading(false);
//            runtime.pushToOperandStack(psObject);
//
        } else {
            fail();
            runtime.pushToOperandStack(psObject);
            return;
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("aload");
    }
}
