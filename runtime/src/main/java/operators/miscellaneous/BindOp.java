package operators.miscellaneous;

import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

import java.util.ArrayList;

/**
 * Created by 1 on 19.03.14.
 */
public class BindOp extends Operator {
    public static final BindOp instance = new BindOp();

    protected BindOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject o = runtime.popFromOperandStack();
        if (!o.isProc()) {
            runtime.pushToOperandStack(o);
            return;
        }
        PSArray psArr = (PSArray) o.getValue();
        ArrayList<PSObject> resArray = new ArrayList<PSObject>();
        for (PSObject innerObj : psArr.getArray()) {
            switch (innerObj.getType()) {
                case NAME:
                    if (innerObj.treatAs() == Attribute.TreatAs.EXECUTABLE) {
                        PSObject value = runtime.findValue(innerObj);
                        if (value.getType() == Type.OPERATOR) {
                            resArray.add(value);
                            break;
                        }

                    }
                default:
                    resArray.add(innerObj);
                    break;
            }
        }
        PSArray result = new PSArray(resArray);
        o.setValue(result);
        runtime.pushToOperandStack(o);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("bind");
    }
}
