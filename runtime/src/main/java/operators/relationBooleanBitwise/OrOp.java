package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class OrOp extends Operator {
    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject o2 = runtime.popFromOperandStack();
        PSObject o1 = runtime.popFromOperandStack();
        if (o2.getType() == Type.BOOLEAN && o1.getType() == Type.BOOLEAN) {
            PSBoolean b1 = (PSBoolean) o1.getValue();
            PSBoolean b2 = (PSBoolean) o2.getValue();
            runtime.pushToOperandStack(new PSObject(PSBoolean.get(b1.getFlag() || b2.getFlag())));
        } else if (o1.getType() == Type.INTEGER && o2.getType() == Type.INTEGER) {
            PSInteger psInteger1 = (PSInteger) o1.getValue();
            PSInteger psInteger2 = (PSInteger) o2.getValue();
            int i1 = psInteger1.getIntValue();
            int i2 = psInteger2.getIntValue();
            runtime.pushToOperandStack(new PSObject(new PSInteger(i1 | i2)));
        } else {
            runtime.pushToOperandStack(o1);
            runtime.pushToOperandStack(o2);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("or");
    }
}
