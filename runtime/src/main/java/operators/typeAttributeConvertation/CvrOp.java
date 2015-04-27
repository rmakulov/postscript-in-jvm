package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;
import runtime.Context;

public class CvrOp extends Operator {

    public static final CvrOp instance = new CvrOp();

    protected CvrOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if(context.getOperandStack().size() < 1)
        {
            fail();
        }
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        if (o.getType() == Type.REAL) {
            context.pushToOperandStack(o);
            return;
        } else if (o.getType() == Type.INTEGER) {
            double d = ((PSInteger) o.getValue()).getRealValue();
            context.pushToOperandStack(new PSObject(new PSReal(d)));
        } else {
            String s = ((PSString) o.getValue()).getString();
            double d = 0;
            try {
                if (s.charAt(0) == '.') s = "0" + s;
                d = (int) Double.parseDouble(s);
            } catch (NumberFormatException e2) {
                context.pushToOperandStack(o);
                return;
            }
            context.pushToOperandStack(new PSObject(new PSReal(d)));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvr");
    }

}