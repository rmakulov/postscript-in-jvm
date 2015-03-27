package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;
import runtime.Context;

public class CvsOp extends Operator {

    public static final CvsOp instance = new CvsOp();

    protected CvsOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject o2 = context.popFromOperandStack();
        if (o2.getType() != Type.STRING) {
            context.pushToOperandStack(o2);
            return;
        }
        PSObject o1 = context.popFromOperandStack();
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
            context.pushToOperandStack(o1);
            context.pushToOperandStack(o2);
            return;
        }
        o2.setValue(psString.setSubstring(0, newString));
        context.pushToOperandStack(new PSObject(new PSString(newString)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvs");
    }
}
