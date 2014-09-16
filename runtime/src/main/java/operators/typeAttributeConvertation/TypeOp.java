package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class TypeOp extends Operator {

    public static final TypeOp instance = new TypeOp();

    protected TypeOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        String objTypeStr = o.type();
        PSObject nameObj = new PSObject(new PSName(objTypeStr), EXECUTABLE);
        runtime.pushToOperandStack(nameObj);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("type");
    }
}
