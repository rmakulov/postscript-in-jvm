package operators.string;

import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by Дмитрий on 25.03.14.
 */
public class StringOp extends Operator {
    public static final StringOp instance = new StringOp();

    protected StringOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) return;
        PSObject o = context.popFromOperandStack();
        if (o.getType() != Type.INTEGER) {
            context.pushToOperandStack(o);
            return;
        }
        PSInteger psInteger = (PSInteger) o.getValue();
        int size = psInteger.getIntValue();
        if (size <= 0) {
            context.pushToOperandStack(o);
            return;
        }
        context.pushToOperandStack(new PSObject(new PSString(size), Attribute.TreatAs.LITERAL));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("string");
    }

}
