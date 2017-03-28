package operators.arithmetic.binary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;
import runtime.Runtime;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class BinaryIntArithmeticOp {
    public static void doOperation(Context context, char op) {
        runtime.Runtime runtime = Runtime.getInstance();
        if (context.getOperandStackSize() < 2) return;
        PSObject o1 = context.popFromOperandStack();
        PSObject o2 = context.popFromOperandStack();
        if (o1.getType() != Type.INTEGER || o2.getType() != Type.INTEGER) {
            context.pushToOperandStack(o2);
            context.pushToOperandStack(o1);
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
                    context.pushToOperandStack(o2);
                    context.pushToOperandStack(o1);
                    return;
                }
                iRes = r1 / r2;
                break;
            default:
                context.pushToOperandStack(o2);
                context.pushToOperandStack(o1);
                return;
        }
        context.pushToOperandStack(new PSObject(new PSInteger(iRes)));
    }
}
