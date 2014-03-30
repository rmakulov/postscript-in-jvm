package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
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
    public void execute() {//x1 y1 x2 y2 x3 y3 curveto –
        if (runtime.getOperandStackSize() < 6 || gState.currentPoint == null) return;
        PSObject oY3 = runtime.popFromOperandStack();
        PSObject oX3 = runtime.popFromOperandStack();
        PSObject oY2 = runtime.popFromOperandStack();
        PSObject oX2 = runtime.popFromOperandStack();
        PSObject oY1 = runtime.popFromOperandStack();
        PSObject oX1 = runtime.popFromOperandStack();
        if (!(oX1.isNumber() && oY1.isNumber() && oX2.isNumber() && oY2.isNumber() && oX3.isNumber() && oY3.isNumber())) {
            runtime.pushToOperandStack(oX1);
            runtime.pushToOperandStack(oY1);
            runtime.pushToOperandStack(oX2);
            runtime.pushToOperandStack(oY2);
            runtime.pushToOperandStack(oX3);
            runtime.pushToOperandStack(oY3);
            return;
        }
        double nX1 = ((PSNumber) oX1.getValue()).getRealValue();
        double nY1 = ((PSNumber) oY1.getValue()).getRealValue();
        double nX2 = ((PSNumber) oX2.getValue()).getRealValue();
        double nY2 = ((PSNumber) oY2.getValue()).getRealValue();
        double nX3 = ((PSNumber) oX3.getValue()).getRealValue();
        double nY3 = ((PSNumber) oY3.getValue()).getRealValue();

        TransformMatrix cTM = gState.cTM;
        PSPoint p1 = cTM.transform(nX1, nY1);
        PSPoint p2 = cTM.transform(nX2, nY2);
        PSPoint p3 = cTM.transform(nX3, nY3);
        gState.currentPath.addCurve(gState.currentPoint, p1, p2, p3);
        gState.currentPoint = p3;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("curveto");
    }
}
