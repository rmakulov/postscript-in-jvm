package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by user on 25.03.14.
 */
public class RCurveToOp extends AbstractGraphicOperator {
    public static final RCurveToOp instance = new RCurveToOp();

    protected RCurveToOp() {
        super();
    }

    @Override
    public void execute() {//dx1 dy1 dx2 dy2 dx3 dy3 rcurveto –
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
        double dX1 = ((PSNumber) oX1.getValue()).getRealValue();
        double dY1 = ((PSNumber) oY1.getValue()).getRealValue();
        double dX2 = ((PSNumber) oX2.getValue()).getRealValue();
        double dY2 = ((PSNumber) oY2.getValue()).getRealValue();
        double dX3 = ((PSNumber) oX3.getValue()).getRealValue();
        double dY3 = ((PSNumber) oY3.getValue()).getRealValue();

        TransformMatrix cTM = gState.cTM;
        PSPoint absP0 = cTM.iTransform(gState.currentPoint);
        PSPoint absP1 = new PSPoint(absP0.getX() + dX1, absP0.getY() + dY1);
        PSPoint absP2 = new PSPoint(absP0.getX() + dX2, absP0.getY() + dY2);
        PSPoint absP3 = new PSPoint(absP0.getX() + dX3, absP0.getY() + dY3);

        PSPoint p1 = cTM.transform(absP1);
        PSPoint p2 = cTM.transform(absP2);
        PSPoint p3 = cTM.transform(absP3);

        gState.currentPath.addCurve(gState.currentPoint, p1, p2, p3);
        gState.currentPoint = p3;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rcurveto");
    }
}
