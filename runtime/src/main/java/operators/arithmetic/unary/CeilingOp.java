package operators.arithmetic.unary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import psObjects.values.simple.numbers.PSReal;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class CeilingOp extends Operator {
    public static final CeilingOp instance = new CeilingOp();

    protected CeilingOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject o1 = runtime.popFromOperandStack();
        double r1 = ((PSNumber) o1.getValue()).getRealValue();
        double dRes = 0;

        try {

            dRes = (Double) asm.getMethod("ceiling", double.class).invoke(null, r1);
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
        return new PSName("ceiling");
    }

    public final static char getSymbolicChar = '^';
}
