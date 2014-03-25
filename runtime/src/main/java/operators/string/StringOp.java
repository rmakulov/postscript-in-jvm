package operators.string;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 25.03.14.
 */
public class StringOp extends Operator {
    public static final StringOp instance = new StringOp();

    protected StringOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject o = runtime.popFromOperandStack();
        if (o.getType() == Type.INTEGER) {
            runtime.pushToOperandStack(o);
            return;
        }
        PSInteger psInteger = (PSInteger) o.getValue();
        int size = psInteger.getIntValue();
        if (size <= 0) {
            runtime.pushToOperandStack(o);
            return;
        }
        String res = "";
        for (int i = 0; i < size; i++) {
            res += res + new Character((char) 0);
        }
        runtime.pushToOperandStack(new PSObject(new PSString(res)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("string");
    }

}
