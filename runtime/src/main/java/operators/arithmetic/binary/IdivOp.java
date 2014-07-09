package operators.arithmetic.binary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class IdivOp extends Operator {
    public static final IdivOp instance = new IdivOp();

    protected IdivOp() {
        super();
    }

    @Override
    public void execute() {

        int r2 = ((PSInteger) runtime.popFromOperandStack().getValue()).getIntValue();
        int r1 = ((PSInteger) runtime.popFromOperandStack().getValue()).getIntValue();

        int iRes = 0;
        try {
            iRes = (Integer) asm.getMethod("idiv", int.class, int.class).invoke(null, r1, r2);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        runtime.pushToOperandStack(new PSObject(new PSInteger(iRes)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("idiv");
    }
}
