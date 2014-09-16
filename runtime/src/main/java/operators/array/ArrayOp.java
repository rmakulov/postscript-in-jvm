package operators.array;

import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.PSNull;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 25.03.14.
 */
public class ArrayOp extends Operator {

    public static final ArrayOp instance = new ArrayOp();

    protected ArrayOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null || o.getType() != Type.INTEGER) {
            runtime.pushToOperandStack(o);
            return;
        }
        PSInteger psInteger = (PSInteger) o.getValue();
        int initSize = psInteger.getIntValue();
        PSObject[] array = new PSObject[initSize];
        for (int i = 0; i < array.length; i++) {
            array[i] = new PSObject(PSNull.NULL);
        }
        PSArray psArray = new PSArray(array);
        runtime.pushToOperandStack(new PSObject(psArray, Attribute.TreatAs.LITERAL));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("array");
    }
}
