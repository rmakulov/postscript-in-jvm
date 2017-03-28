package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;
import runtime.Context;

public class CvrsOp extends Operator {

    public static final CvrsOp instance = new CvrsOp();

    protected CvrsOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 3) return;
        PSObject o3 = context.popFromOperandStack();
        PSObject o2 = context.popFromOperandStack();
        PSObject o1 = context.popFromOperandStack();

        if (!o1.isNumber() || o2.getType() != Type.INTEGER || o3.getType() != Type.STRING) {
            returnPSObjectOnStack(context, o1, o2, o3);
            return;
        }
        PSReal psNum = (PSReal) o1.getValue();
        PSInteger psRadix = (PSInteger) o2.getValue();
        PSString psString = (PSString) o3.getValue();
        double num = psNum.getRealValue();
        int radix = psRadix.getIntValue();
        String str = psString.getString();
        String newString;

        if (radix == 10) {
            newString = Double.toString(num);
        } else {
            newString = Integer.toString((int) num, radix);
        }
        if (str.length() < newString.length()) {
            //todo error
            returnPSObjectOnStack(context, o1, o2, o3);
            return;
        }
        o3.setValue(psString.setSubstring(0, newString));
        context.pushToOperandStack(new PSObject(new PSString(newString)));
    }

    private void returnPSObjectOnStack(Context context, PSObject o1, PSObject o2, PSObject o3) {
        context.pushToOperandStack(o1);
        context.pushToOperandStack(o2);
        context.pushToOperandStack(o3);
        return;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvrs");
    }
}
