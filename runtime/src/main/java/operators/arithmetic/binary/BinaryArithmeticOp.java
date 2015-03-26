package operators.arithmetic.binary;


import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSNumber;
import psObjects.values.simple.numbers.PSReal;
import runtime.Context;
import runtime.Runtime;

public class BinaryArithmeticOp {
    public static void doOperation(Context context, char op) {
        runtime.Runtime runtime = Runtime.getInstance();
        if (context.getOperandStackSize() < 2) return;
        PSObject o2 = context.popFromOperandStack();
        PSObject o1 = context.popFromOperandStack();
        if (!o2.isNumber() || !o1.isNumber()) {
            context.pushToOperandStack(o1);
            context.pushToOperandStack(o2);
            return;
        }
        PSNumber i1 = (PSNumber) o1.getValue();
        PSNumber i2 = (PSNumber) o2.getValue();
        double r1 = i1.getRealValue();
        double r2 = i2.getRealValue();
        double dRes;
        switch (op) {
            case '+':
                if (o1.getType() == Type.INTEGER && o2.getType() == Type.INTEGER) {
                    context.pushToOperandStack(new PSObject(new PSInteger(((int) r1) + ((int) r2))));
                    return;
                }
                dRes = r1 + r2;
                break;
            case '-':
                if (o1.getType() == Type.INTEGER && o2.getType() == Type.INTEGER) {
                    context.pushToOperandStack(new PSObject(new PSInteger(((int) r1) - ((int) r2))));
                    return;
                }
                dRes = r1 - r2;
                break;
            case '/':
                dRes = r1 / r2;
                break;
            case '*':
                if (o1.getType() == Type.INTEGER && o2.getType() == Type.INTEGER) {
                    context.pushToOperandStack(new PSObject(new PSInteger(((int) r1) * ((int) r2))));
                    return;
                }
                dRes = r1 * r2;
                break;
            case 't':
                if (r1 == 0 && r2 == 0) {
                    context.pushToOperandStack(o1);
                    context.pushToOperandStack(o2);
                    return;
                }
                dRes = Math.atan2(r1, r2) * Math.PI / 180;
                break;
            case 'e':
                if (o2.getType() != Type.INTEGER && r1 < 0) {
                    context.pushToOperandStack(o1);
                    context.pushToOperandStack(o2);
                    return;
                }
                dRes = Math.pow(r1, r2);
                break;
            default:
                context.pushToOperandStack(o1);
                context.pushToOperandStack(o2);
                return;
        }
        context.pushToOperandStack(new PSObject(new PSReal(dRes)));
    }
}
