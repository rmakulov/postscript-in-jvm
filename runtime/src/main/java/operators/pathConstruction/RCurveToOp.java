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
 */
public class RCurveToOp extends AbstractGraphicOperator {
    public static final RCurveToOp instance = new RCurveToOp();

    protected RCurveToOp() {
        super();
    }

    @Override
    public void interpret(Context context) {//dx1 dy1 dx2 dy2 dx3 dy3 rcurveto –
        if (context.getOperandStackSize() < 6 || context.getGState().currentPoint == null) return;
        PSObject oY3 = context.popFromOperandStack();
        PSObject oX3 = context.popFromOperandStack();
        PSObject oY2 = context.popFromOperandStack();
        PSObject oX2 = context.popFromOperandStack();
        PSObject oY1 = context.popFromOperandStack();
        PSObject oX1 = context.popFromOperandStack();
        if (!(oX1.isNumber() && oY1.isNumber() && oX2.isNumber() && oY2.isNumber() && oX3.isNumber() && oY3.isNumber())) {
            context.pushToOperandStack(oX1);
            context.pushToOperandStack(oY1);
            context.pushToOperandStack(oX2);
            context.pushToOperandStack(oY2);
            context.pushToOperandStack(oX3);
            context.pushToOperandStack(oY3);
            return;
        }
        double dX1 = ((PSNumber) oX1.getValue()).getRealValue();
        double dY1 = ((PSNumber) oY1.getValue()).getRealValue();
        double dX2 = ((PSNumber) oX2.getValue()).getRealValue();
        double dY2 = ((PSNumber) oY2.getValue()).getRealValue();
        double dX3 = ((PSNumber) oX3.getValue()).getRealValue();
        double dY3 = ((PSNumber) oY3.getValue()).getRealValue();

        TransformMatrix cTM = context.getGState().cTM;
        PSPoint absP0 = cTM.iTransform(context.getGState().currentPoint);
        PSPoint absP1 = new PSPoint(absP0.getX() + dX1, absP0.getY() + dY1);
        PSPoint absP2 = new PSPoint(absP0.getX() + dX2, absP0.getY() + dY2);
        PSPoint absP3 = new PSPoint(absP0.getX() + dX3, absP0.getY() + dY3);

        PSPoint p1 = cTM.transform(absP1);
        PSPoint p2 = cTM.transform(absP2);
        PSPoint p3 = cTM.transform(absP3);

        context.getGState().currentPath.addCurve(context.getGState().currentPoint, p1, p2, p3);
        context.getGState().currentPoint = p3;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rcurveto");
    }
}
