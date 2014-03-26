package operators.miscellaneous;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.PSNull;

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
            resArray.addAll(bind(innerObj));
        }
        PSArray result = new PSArray(resArray.size());
        for (int i = 0; i < resArray.size(); i++) {
            result = result.setValue(i, resArray.get(resArray.size() - i - 1));
        }
        o.setValue(result);
        runtime.pushToOperandStack(o);
    }

    private ArrayList<PSObject> bind(PSObject o) {
        ArrayList<PSObject> resArray;
        Type type = o.getType();
        switch (type) {
            case NAME:
                PSObject value = runtime.findValue(o);
                if (value.getValue().equals(PSNull.NULL)) {
                    resArray = new ArrayList<PSObject>();
                    resArray.add(o);
                } else {
                    resArray = bind(value);
                }
                break;
            case ARRAY:
            case PACKEDARRAY:
                resArray = new ArrayList<PSObject>();
                if (o.xcheck()) {
                    for (PSObject innerObj : ((PSArray) o.getValue()).getArray()) {
                        resArray.addAll(bind(innerObj));
                    }
                } else {
                    resArray.add(o);

                }
                break;
            case INTEGER:
            case REAL:
            case BOOLEAN:
            case FONT_ID:
            case NULL:
            case MARK:
            case OPERATOR:
            case DICTIONARY:
            case STRING:
            case FILE:
            case GSTATE:
            case SAVE:
                resArray = new ArrayList<PSObject>();
                resArray.add(o);
                break;
            default:
                resArray = new ArrayList<PSObject>();
                resArray.add(o);
        }
        return resArray;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("bind");
    }
}
