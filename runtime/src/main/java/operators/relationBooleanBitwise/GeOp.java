package operators.relationBooleanBitwise;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class GeOp extends Operator {

    public static final GeOp instance = new GeOp();

    protected GeOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject o2 = runtime.popFromOperandStack();
        PSObject o1 = runtime.popFromOperandStack();
        if (o2.isNumber() && o1.isNumber()) {
            PSNumber i1 = (PSNumber) o1.getValue();
            PSNumber i2 = (PSNumber) o2.getValue();
            PSObject psObject = new PSObject(PSBoolean.get(i1.psCompareTo(i2) >= 0));
            runtime.pushToOperandStack(psObject);
        } else if (o1.getType() == Type.STRING && o2.getType() == Type.STRING) {
            PSString s1 = (PSString) o1.getValue();
            PSString s2 = (PSString) o2.getValue();
            runtime.pushToOperandStack(new PSObject(PSBoolean.get(s1.psCompareTo(s2) >= 0)));
        } else {
            runtime.pushToOperandStack(o1);
            runtime.pushToOperandStack(o2);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("ge");
    }
}
