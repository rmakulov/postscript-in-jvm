package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;

public class CvrsOp extends Operator {

    public static final CvrsOp instance = new CvrsOp();

    protected CvrsOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 3) return;
        PSObject o3 = runtime.popFromOperandStack();
        PSObject o2 = runtime.popFromOperandStack();
        PSObject o1 = runtime.popFromOperandStack();

        if (!o1.isNumber() || o2.getType() != Type.INTEGER || o3.getType() != Type.STRING) {
            returnPSObjectOnStack(o1, o2, o3);
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
            returnPSObjectOnStack(o1, o2, o3);
            return;
        }
        o3.setValue(psString.setSubstring(0, newString));
        runtime.pushToOperandStack(new PSObject(new PSString(newString)));
    }

    private void returnPSObjectOnStack(PSObject o1, PSObject o2, PSObject o3) {
        runtime.pushToOperandStack(o1);
        runtime.pushToOperandStack(o2);
        runtime.pushToOperandStack(o3);
        return;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvrs");
    }
}
