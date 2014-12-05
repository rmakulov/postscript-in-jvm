package operators.arithmetic.unary;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSNumber;
import psObjects.values.simple.numbers.PSReal;
import runtime.Runtime;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class UnaryArithmeticOp {
    public static void doOperation(char op) {
        runtime.Runtime runtime = Runtime.getInstance();
        PSObject o = runtime.popFromOperandStack();
        if (o == null || !o.isNumber()) {
            runtime.pushToOperandStack(o);
            return;
        }
        PSNumber i = (PSNumber) o.getValue();
        double r = i.getRealValue();
        double dRes;
        int iRes;
        boolean isInt = o.getType() == Type.INTEGER;
        switch (op) {
            case AbsOp.getSymbolicChar:
                if (isInt) {
                    iRes = (int) Math.abs(r);
                    runtime.pushToOperandStack(new PSObject(new PSInteger(iRes)));
                    return;
                }
                dRes = Math.abs(r);
                break;
            case CeilingOp.getSymbolicChar:
                if (isInt) {
                    iRes = (int) Math.ceil(r);
                    runtime.pushToOperandStack(new PSObject(new PSInteger(iRes)));
                    return;
                }
                dRes = Math.ceil(r);
                break;
            case CosOp.getSymbolicChar:
                dRes = Math.cos(r * Math.PI / 180);
                break;
            case FloorOp.getSymbolicChar:
                if (isInt) {
                    iRes = (int) Math.floor(r);
                    runtime.pushToOperandStack(new PSObject(new PSInteger(iRes)));
                    return;
                }
                dRes = Math.floor(r);
                break;
            case LnOp.getSymbolicChar:
                dRes = Math.log(r);
                break;
            case LogOp.getSymbolicChar:
                dRes = Math.log10(r);
                break;
            case NegOp.getSymbolicChar:
                if (isInt) {
                    iRes = (int) -r;
                    runtime.pushToOperandStack(new PSObject(new PSInteger(iRes)));
                    return;
                }
                dRes = -r;
                break;
            case RoundOp.getSymbolicChar:
                if (isInt) {
                    iRes = (int) r;
                    runtime.pushToOperandStack(new PSObject(new PSInteger(iRes)));
                    return;
                }
                dRes = Math.round(r);
                break;
            case SinOp.getSymbolicChar:
                dRes = Math.sin(r * Math.PI / 180);
                break;
            case SqrtOp.getSymbolicChar:
                dRes = Math.sqrt(r);
                break;
            case TruncateOp.getSymbolicChar:
                if (isInt) {
                    iRes = (int) r;
                    runtime.pushToOperandStack(new PSObject(new PSInteger(iRes)));
                    return;
                }
                dRes = (int) r;
                break;
            default:
                runtime.pushToOperandStack(o);
                return;
        }
        runtime.pushToOperandStack(new PSObject(new PSReal(dRes)));
    }
}
