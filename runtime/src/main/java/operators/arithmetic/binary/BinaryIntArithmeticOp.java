package operators.arithmetic.binary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Runtime;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class BinaryIntArithmeticOp {
    public static void doOperation(char op) {
        runtime.Runtime runtime = Runtime.getInstance();
        if (runtime.getOperandStackSize() < 2) return;
        PSObject o1 = runtime.popFromOperandStack();
        PSObject o2 = runtime.popFromOperandStack();
        if (o1.getType() != Type.INTEGER || o2.getType() != Type.INTEGER) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            return;
        }
        PSInteger i1 = (PSInteger) o2.getValue();
        PSInteger i2 = (PSInteger) o1.getValue();
        int r1 = i1.getIntValue();
        int r2 = i2.getIntValue();
        int iRes;
        switch (op) {
            case '%':
                iRes = r1 % r2;
                break;
            case '/':
                if (r2 == 0) {
                    runtime.pushToOperandStack(o2);
                    runtime.pushToOperandStack(o1);
                    return;
                }
                iRes = r1 / r2;
                break;
            default:
                runtime.pushToOperandStack(o2);
                runtime.pushToOperandStack(o1);
                return;
        }
        runtime.pushToOperandStack(new PSObject(new PSInteger(iRes)));
    }
}
