package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class LeOp extends Operator {

    public static final LeOp instance = new LeOp();

    protected LeOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject o2 = context.popFromOperandStack();
        PSObject o1 = context.popFromOperandStack();
        if (o2.isNumber() && o1.isNumber()) {
            PSNumber i1 = (PSNumber) o1.getValue();
            PSNumber i2 = (PSNumber) o2.getValue();
            context.pushToOperandStack(new PSObject(PSBoolean.get(i1.psCompareTo(i2) <= 0)));
        } else if (o1.getType() == Type.STRING && o2.getType() == Type.STRING) {
            PSString s1 = (PSString) o1.getValue();
            PSString s2 = (PSString) o2.getValue();
            context.pushToOperandStack(new PSObject(PSBoolean.get(s1.psCompareTo(s2) <= 0)));
        } else {
            context.pushToOperandStack(o1);
            context.pushToOperandStack(o2);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("le");
    }
}
