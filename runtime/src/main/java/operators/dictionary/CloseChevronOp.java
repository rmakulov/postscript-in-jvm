package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;

import java.util.ArrayList;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class CloseChevronOp extends Operator {
    public static final CloseChevronOp instance = new CloseChevronOp();

    protected CloseChevronOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject.getValue() != PSMark.CLOSE_CHEVRON_BRACKET) {
            runtime.pushToOperandStack(psObject);
        }
        ArrayList<PSObject> entries = new ArrayList<PSObject>();
        PSObject obj2, obj1;
        while ((obj2 = runtime.popFromOperandStack()).getValue() != PSMark.OPEN_CHEVRON_BRACKET &&
                (obj1 = runtime.popFromOperandStack()).getValue() != PSMark.OPEN_CHEVRON_BRACKET) {
            entries.add(obj1);
            entries.add(obj2);
        }
        PSDictionary dict = new PSDictionary(entries);
        runtime.pushToOperandStack(new PSObject(dict));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName(">>");
    }
}
