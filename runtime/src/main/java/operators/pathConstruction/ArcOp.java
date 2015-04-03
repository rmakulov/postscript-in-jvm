package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.Context;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by user on 15.03.14.
 */
public class ArcOp extends AbstractGraphicOperator {
    public static final ArcOp instance = new ArcOp();

    protected ArcOp() {
        super();
    }

    @Override
    public void interpret(Context context) {//x y r angle1 angle2 arc ---
        //todo
        if (context.getOperandStackSize() < 5) return;
        PSObject oAngle2 = context.popFromOperandStack();
        PSObject oAngle1 = context.popFromOperandStack();
        PSObject oR = context.popFromOperandStack();
        PSObject oY = context.popFromOperandStack();
        PSObject oX = context.popFromOperandStack();

        if (!(oAngle2.isNumber() && oAngle1.isNumber() && oR.isNumber() && oY.isNumber() && oX.isNumber())) {
            context.pushToOperandStack(oX);
            context.pushToOperandStack(oY);
            context.pushToOperandStack(oR);
            context.pushToOperandStack(oAngle1);
            context.pushToOperandStack(oAngle2);
            return;
        }
        double nAngle2 = ((PSNumber) oAngle2.getValue()).getRealValue();
        double nAngle1 = ((PSNumber) oAngle1.getValue()).getRealValue();
        double nR = ((PSNumber) oR.getValue()).getRealValue();
        double nY = ((PSNumber) oY.getValue()).getRealValue();
        double nX = ((PSNumber) oX.getValue()).getRealValue();



        TransformMatrix cTM = context.getGState().cTM;
        PSPoint absCent = cTM.transform(nX, nY);
        double xScale = cTM.getXScale();
        double yScale = cTM.getYScale();
        double xR = nR * xScale;
        double yR = nR * yScale;
        double rotateAngle = cTM.getRotateAngle();
        nAngle1 = nAngle1 + rotateAngle;
        nAngle2 = nAngle2 + rotateAngle;

        double xEnd = absCent.getX() + xR * Math.cos(nAngle2 * Math.PI / 180);
        double yEnd = absCent.getY() + yR * Math.sin(nAngle2 * Math.PI / 180);


        PSPoint absEnd = new PSPoint(xEnd, yEnd);
        boolean connect = context.getGState().currentPoint != null;

        context.getGState().currentPath.addArc(absCent, xR, yR,
                nAngle1, nAngle2, false, connect);
        context.getGState().currentPoint = absEnd;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("arc");
    }
}
