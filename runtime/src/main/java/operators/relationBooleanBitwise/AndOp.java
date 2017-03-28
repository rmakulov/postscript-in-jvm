package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class AndOp extends Operator {

    public static final AndOp instance = new AndOp();

    protected AndOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject o2 = context.popFromOperandStack();
        PSObject o1 = context.popFromOperandStack();
        if (o2.getType() == Type.BOOLEAN && o1.getType() == Type.BOOLEAN) {
            PSBoolean b1 = (PSBoolean) o1.getValue();
            PSBoolean b2 = (PSBoolean) o2.getValue();
            context.pushToOperandStack(new PSObject(PSBoolean.get(b1.getFlag() && b2.getFlag())));
        } else if (o1.getType() == Type.INTEGER && o2.getType() == Type.INTEGER) {
            PSInteger psInteger1 = (PSInteger) o1.getValue();
            PSInteger psInteger2 = (PSInteger) o2.getValue();
            int i1 = psInteger1.getIntValue();
            int i2 = psInteger2.getIntValue();
            context.pushToOperandStack(new PSObject(new PSInteger(i1 & i2)));
        } else {
            context.pushToOperandStack(o1);
            context.pushToOperandStack(o2);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("and");
    }
}
