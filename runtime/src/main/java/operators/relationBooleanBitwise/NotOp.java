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
public class NotOp extends Operator {

    public static final NotOp instance = new NotOp();

    protected NotOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        if (o.getType() == Type.BOOLEAN) {
            PSBoolean b = (PSBoolean) o.getValue();
            runtime.pushToOperandStack(new PSObject(b.not()));
        } else if (o.getType() == Type.INTEGER) {
            PSInteger psInteger = (PSInteger) o.getValue();
            int i = psInteger.getIntValue();
            runtime.pushToOperandStack(new PSObject(new PSInteger(~i)));
        } else {
            runtime.pushToOperandStack(o);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("not");
    }
}
