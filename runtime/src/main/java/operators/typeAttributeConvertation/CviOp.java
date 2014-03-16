package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class CviOp extends Operator {
    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        if (o.getType() == Type.INTEGER) {
            runtime.pushToOperandStack(o);
            return;
        } else if (o.getType() == Type.REAL) {
            double d = ((PSReal) o.getValue()).getRealValue();
            int truncatedInt = (int) d;
            runtime.pushToOperandStack(new PSObject(new PSInteger(truncatedInt)));
        }
        String s = ((PSString) o.getValue()).getString();
        int i = 0;
        try {
            parseAsInteger(s);
        } catch (NumberFormatException e2) {
            runtime.pushToOperandStack(o);
            return;
        }
        runtime.pushToOperandStack(new PSObject(new PSInteger(i)));

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
