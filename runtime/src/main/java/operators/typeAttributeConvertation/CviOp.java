package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class CviOp extends Operator {

    public static final CviOp instance = new CviOp();

    protected CviOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        if (o.getType() == Type.INTEGER) {
            context.pushToOperandStack(o);
            return;
        } else if (o.getType() == Type.REAL) {
            double d = ((PSReal) o.getValue()).getRealValue();
            int truncatedInt = (int) d;
            context.pushToOperandStack(new PSObject(new PSInteger(truncatedInt)));
            return;
        }
        String s = ((PSString) o.getValue()).getString();
        int i = 0;
        try {
            parseAsInteger(s);
        } catch (NumberFormatException e2) {
            context.pushToOperandStack(o);
            return;
        }
        context.pushToOperandStack(new PSObject(new PSInteger(i)));

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvi");
    }

    public static int parseAsInteger(String s) throws NumberFormatException {
        int i = 0;
        try {
            //treat as integer;
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            //treat as Real
            try {
                if (s.charAt(0) == '.') s = "0" + s;
                i = (int) Double.parseDouble(s);
            } catch (NumberFormatException e1) {
                //treat as radix
                String[] radixNum = s.split("#");
                int radix = Integer.parseInt(radixNum[0]);
                i = Integer.parseInt(radixNum[1], radix);

            }
        }
        return i;
    }
}
