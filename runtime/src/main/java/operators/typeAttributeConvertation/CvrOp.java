package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSReal;

public class CvrOp extends Operator {

    public static final CvrOp instance = new CvrOp();

    protected CvrOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        if (o.getType() == Type.REAL) {
            runtime.pushToOperandStack(o);
            return;
        } else if (o.getType() == Type.INTEGER) {
            double d = ((PSReal) o.getValue()).getRealValue();
            runtime.pushToOperandStack(new PSObject(new PSReal(d)));
        }
        String s = ((PSString) o.getValue()).getString();
        double d = 0;
        try {
            if (s.charAt(0) == '.') s = "0" + s;
            d = (int) Double.parseDouble(s);
        } catch (NumberFormatException e2) {
            runtime.pushToOperandStack(o);
            return;
        }
        runtime.pushToOperandStack(new PSObject(new PSReal(d)));

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvr");
    }

}