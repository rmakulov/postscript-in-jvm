package operators.dictionary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 25.03.14.
 */
public class DictOp extends Operator {
    public static final DictOp instance = new DictOp();

    protected DictOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null || psObject.getType() != Type.INTEGER)
            return;

        //todo init dictionary with int
        runtime.pushToDictionaryStack(new PSObject(new PSDictionary()));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("dict");
    }
}
