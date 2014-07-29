package operators.arithmetic.unary;

import psObjects.PSObject;
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
        switch (op) {
            case AbsOp.getSymbolicChar:
                dRes = Math.abs(r);
                break;
            case CeilingOp.getSymbolicChar:
                dRes = Math.ceil(r);
                break;
            case CosOp.getSymbolicChar:
                dRes = Math.cos(r * Math.PI / 180);
                break;
            case FloorOp.getSymbolicChar:
                dRes = Math.floor(r);
                break;
            case LnOp.getSymbolicChar:
                dRes = Math.log(r);
                break;
            case LogOp.getSymbolicChar:
                dRes = Math.log10(r);
                break;
            case NegOp.getSymbolicChar:
                dRes = -r;
                break;
            case RoundOp.getSymbolicChar:
                dRes = Math.round(r);
                break;
            case SinOp.getSymbolicChar:
                dRes = Math.sin(r * Math.PI / 180);
                break;
            case SqrtOp.getSymbolicChar:
                dRes = Math.sqrt(r);
                break;
            case TruncateOp.getSymbolicChar:
                dRes = (int) r;
                break;
            default:
                runtime.pushToOperandStack(o);
                return;
        }
        runtime.pushToOperandStack(new PSObject(new PSReal(dRes)));
    }
}
