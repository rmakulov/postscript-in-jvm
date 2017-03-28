package operators.dictionary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 25.03.14.
 */
public class DictOp extends Operator {
    public static final DictOp instance = new DictOp();

    protected DictOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) return;
        PSObject psObject = context.popFromOperandStack();
        if (psObject.getType() != Type.INTEGER) {
            context.pushToOperandStack(psObject);
            return;
        }

        //todo init dictionary with int
        context.pushToOperandStack(new PSObject(new PSDictionary()));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("dict");
    }
}
