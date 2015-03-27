package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.Context;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by user on 25.03.14.
 * appends a section of a cubic Bézier curve to the current path between the current
 * point (x0, y0) and the endpoint (x3, y3), using (x1, y1) and (x2, y2) as the Bézier control
 * points. The endpoint (x3, y3) becomes the new current point. If the current
 * point is undefined because the current path is empty, a nocurrentpoint error
 * occurs.
 */
public class CurveToOp extends AbstractGraphicOperator {
    public static final CurveToOp instance = new CurveToOp();

    protected CurveToOp() {
        super();
    }

    @Override
    public void interpret(Context context) {//x1 y1 x2 y2 x3 y3 curveto –
        if (context.getOperandStackSize() < 6 || context.getGState().currentPoint == null) return;
        PSObject oY3 = context.popFromOperandStack();
        PSObject oX3 = context.popFromOperandStack();
        PSObject oY2 = context.popFromOperandStack();
        PSObject oX2 = context.popFromOperandStack();
        PSObject oY1 = context.popFromOperandStack();
        PSObject oX1 = context.popFromOperandStack();
        if (wrongArgs(context, oY3, oX3, oY2, oX2, oY1, oX1)) return;
        double nX1 = ((PSNumber) oX1.getValue()).getRealValue();
        double nY1 = ((PSNumber) oY1.getValue()).getRealValue();
        double nX2 = ((PSNumber) oX2.getValue()).getRealValue();
        double nY2 = ((PSNumber) oY2.getValue()).getRealValue();
        double nX3 = ((PSNumber) oX3.getValue()).getRealValue();
        double nY3 = ((PSNumber) oY3.getValue()).getRealValue();

        TransformMatrix cTM = context.getGState().cTM;
        PSPoint p1 = cTM.transform(nX1, nY1);
        PSPoint p2 = cTM.transform(nX2, nY2);
        PSPoint p3 = cTM.transform(nX3, nY3);
        context.getGState().currentPath.addCurve(context.getGState().currentPoint, p1, p2, p3);
        context.getGState().currentPoint = p3;
    }

    private boolean wrongArgs(Context context, PSObject oY3, PSObject oX3, PSObject oY2, PSObject oX2, PSObject oY1, PSObject oX1) {
        if (!(oX1.isNumber() && oY1.isNumber() && oX2.isNumber() && oY2.isNumber() && oX3.isNumber() && oY3.isNumber())) {
            fail();
            context.pushToOperandStack(oX1);
            context.pushToOperandStack(oY1);
            context.pushToOperandStack(oX2);
            context.pushToOperandStack(oY2);
            context.pushToOperandStack(oX3);
            context.pushToOperandStack(oY3);
            System.out.println("TROUBLE!!!!");
            return true;
        }
        return false;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("curveto");
    }
}
