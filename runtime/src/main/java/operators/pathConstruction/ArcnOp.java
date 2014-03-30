package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by user on 15.03.14.
 */
public class ArcnOp extends AbstractGraphicOperator {
    public static final ArcnOp instance = new ArcnOp();

    protected ArcnOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 5) return;
        PSObject oAngle2 = runtime.popFromOperandStack();
        PSObject oAngle1 = runtime.popFromOperandStack();
        PSObject oR = runtime.popFromOperandStack();
        PSObject oY = runtime.popFromOperandStack();
        PSObject oX = runtime.popFromOperandStack();

        if (!(oAngle2.isNumber() && oAngle1.isNumber() && oR.isNumber() && oY.isNumber() && oX.isNumber())) {
            runtime.pushToOperandStack(oX);
            runtime.pushToOperandStack(oY);
            runtime.pushToOperandStack(oR);
            runtime.pushToOperandStack(oAngle1);
            runtime.pushToOperandStack(oAngle2);
            return;
        }
        double nAngle2 = ((PSNumber) oAngle2.getValue()).getRealValue();
        double nAngle1 = ((PSNumber) oAngle1.getValue()).getRealValue();
        double nR = ((PSNumber) oR.getValue()).getRealValue();
        double nY = ((PSNumber) oY.getValue()).getRealValue();
        double nX = ((PSNumber) oX.getValue()).getRealValue();

        TransformMatrix cTM = gState.cTM;
        PSPoint absCent = cTM.transform(nX, nY);
        double xScale = cTM.getXScale();
        double yScale = cTM.getYScale();
        double xR = nR * xScale;
        double yR = nR * yScale;
        double rotateAngle = cTM.getRotateAngle();
        nAngle1 = nAngle1 + rotateAngle;
        nAngle2 = nAngle2 + rotateAngle;
        if (nAngle1 - nAngle2 < 360) {
            double temp = nAngle2;
            nAngle2 = nAngle1 + 360;
            nAngle1 = temp;

        }
        double xBegin = absCent.getX() + xR * Math.cos(nAngle1 * Math.PI / 180);
        double yBegin = absCent.getY() + yR * Math.sin(nAngle1 * Math.PI / 180);
        double xEnd = absCent.getX() + xR * Math.cos(nAngle2 * Math.PI / 180);
        double yEnd = absCent.getY() + yR * Math.sin(nAngle2 * Math.PI / 180);

        PSPoint absBegin = new PSPoint(xBegin, yBegin);
        PSPoint absEnd = new PSPoint(xEnd, yEnd);
        boolean connect = gState.currentPoint != null;
        gState.currentPath.addArc(absBegin, absEnd, absCent, xR, yR,
                nAngle1, nAngle2, false, connect);
        gState.currentPoint = new PSPoint(absCent.getX() + xR * Math.cos(nAngle2),
                absCent.getY() + yR * Math.sin(nAngle2));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("arcn");
    }
}
