package operators.arithmetic.binary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import psObjects.values.simple.numbers.PSReal;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class AtanOp extends Operator {
    public static final AtanOp instance = new AtanOp();

    protected AtanOp() {
        super();
    }

    @Override
    public void execute() {

        PSObject o2 = runtime.popFromOperandStack();
        PSObject o1 = runtime.popFromOperandStack();

        double r1 = ((PSNumber) o1.getValue()).getRealValue();
        double r2 = ((PSNumber) o2.getValue()).getRealValue();

        double dRes = 0;
        try {
            if (r1 == 0 && r2 == 0) {
                runtime.pushToOperandStack(o1);
                runtime.pushToOperandStack(o2);
                return;
            }
            dRes = (Double) asm.getMethod("atan", double.class, double.class).invoke(null, r1, r2);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        runtime.pushToOperandStack(new PSObject(new PSReal(dRes)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("atan");
    }

    public final static char getSymbolicChar = 't';
}
