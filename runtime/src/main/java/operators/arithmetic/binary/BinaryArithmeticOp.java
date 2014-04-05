package operators.arithmetic.binary;


import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSNumber;
import psObjects.values.simple.numbers.PSReal;
import runtime.Runtime;

public class BinaryArithmeticOp {
    public static void doOperation(char op) {
        runtime.Runtime runtime = Runtime.getInstance();
        if (runtime.getOperandStackSize() < 2) return;
        PSObject o2 = runtime.popFromOperandStack();
        PSObject o1 = runtime.popFromOperandStack();
        if (!o2.isNumber() || !o1.isNumber()) {
            runtime.pushToOperandStack(o1);
            runtime.pushToOperandStack(o2);
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
                    runtime.pushToOperandStack(new PSObject(new PSInteger(((int) r1) + ((int) r2))));
                    return;
                }
                dRes = r1 + r2;
                break;
            case '-':
                if (o1.getType() == Type.INTEGER && o2.getType() == Type.INTEGER) {
                    runtime.pushToOperandStack(new PSObject(new PSInteger(((int) r1) - ((int) r2))));
                    return;
                }
                dRes = r1 - r2;
                break;
            case '/':
                dRes = r1 / r2;
                break;
            case '*':
                if (o1.getType() == Type.INTEGER && o2.getType() == Type.INTEGER) {
                    runtime.pushToOperandStack(new PSObject(new PSInteger(((int) r1) * ((int) r2))));
                    return;
                }
                dRes = r1 * r2;
                break;
            case 't':
                if (r1 == 0 && r2 == 0) {
                    runtime.pushToOperandStack(o1);
                    runtime.pushToOperandStack(o2);
                    return;
                }
                dRes = Math.atan2(r1, r2) * Math.PI / 180;
                break;
            case 'e':
                if (o2.getType() != Type.INTEGER && r1 < 0) {
                    runtime.pushToOperandStack(o1);
                    runtime.pushToOperandStack(o2);
                    return;
                }
                dRes = Math.pow(r1, r2);
                break;
            default:
                runtime.pushToOperandStack(o1);
                runtime.pushToOperandStack(o2);
                return;
        }
        runtime.pushToOperandStack(new PSObject(new PSReal(dRes)));
    }
}
