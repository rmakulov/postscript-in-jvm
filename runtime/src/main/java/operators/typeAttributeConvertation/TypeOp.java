package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

public class TypeOp extends Operator {

    public static final TypeOp instance = new TypeOp();

    protected TypeOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        String objTypeStr = o.type();
        PSObject nameObj = new PSObject(new PSName(objTypeStr), EXECUTABLE);
        context.pushToOperandStack(nameObj);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("type");
    }
}
