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

/*
            cvs

(convert to string) produces a text representation of an arbitrary object any, stores
the text into string (overwriting some initial portion of its value), and returns a
string object designating the substring actually used. If string is too small to hold
the result of the conversion, a rangecheck error occurs.
If any is a number, cvs produces a string representation of that number. If any is a
boolean value, cvs produces either the string true or the string false . If any is a
string, cvs copies its contents into string . If any is a name or an operator, cvs pro-
duces the text representation of that name or the operator’s name. If any is any
other type, cvs produces the text --nostringval-- .
If any is a real number, the precise format of the result string is implementation-
dependent and not under program control. For example, the value 0.001 might be
represented as 0.001 or as 1.0E−3 .

Examples

/str 20 string def
123 456 add str cvs  % --- (579)
mark str cvs         % --- (--nostringval--)
* */

public class CvsOp extends Operator {

    public static final CvsOp instance = new CvsOp();

    protected CvsOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) return;
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
        //PSObject oRes = new PSObject(new PSString(newString));
        //context.pushToOperandStack(oRes);
        context.pushToOperandStack(o2);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvs");
    }
}
