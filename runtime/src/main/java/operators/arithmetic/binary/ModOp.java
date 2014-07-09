package operators.arithmetic.binary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class ModOp extends Operator {
    public static final ModOp instance = new ModOp();

    protected ModOp() {
        super();
    }

    @Override
    public void execute() {

        PSObject o2 = runtime.popFromOperandStack();
        PSObject o1 = runtime.popFromOperandStack();

        int r2 = ((PSInteger) o2.getValue()).getIntValue();
        int r1 = ((PSInteger) o1.getValue()).getIntValue();

        int iRes = 0;
        try {
            if (r2 == 0) {
                runtime.pushToOperandStack(o2);
                runtime.pushToOperandStack(o1);
                return;
            }
            iRes = (Integer) asm.getMethod("mod", int.class, int.class).invoke(null, r1, r2);
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
        return new PSName("mod");
    }
}
