package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class TypeOp extends Operator {
    @Override
    public void execute() {
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
