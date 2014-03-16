package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;

public class CvsOp extends Operator {

    public static final CvsOp instance = new CvsOp();

    protected CvsOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject o2 = runtime.popFromOperandStack();
        if (o2.getType() != Type.STRING) {
            runtime.pushToOperandStack(o2);
            return;
        }
        PSObject o1 = runtime.popFromOperandStack();
        String newString;
        switch (o1.getType()) {
            case BOOLEAN:
                newString = ((PSBoolean) o1.getValue()).getFlag() + "";
                break;
            case INTEGER:
                newString = ((PSInteger) o1.getValue()).getIntValue() + "";
                break;
            case REAL:
                newString = ((PSReal) o1.getValue()).getRealValue() + "";
                break;
            case STRING:
                newString = ((PSString) o1.getValue()).getString();
                break;
            default:
                newString = "--nostringval--";
        }
        PSString psString = (PSString) o2.getValue();
        if (psString.getString().length() < newString.length()) {
            runtime.pushToOperandStack(o1);
            runtime.pushToOperandStack(o2);
            return;
        }
        o2.setValue(psString.setSubstring(newString));
        runtime.pushToOperandStack(new PSObject(new PSString(newString)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvs");
    }
}
