package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.graphics.figures.PSPoint;

/**
 * Created by user on 15.03.14.
 */
public class ArcOp extends AbstractGraphicOperator {
    public static final ArcOp instance = new ArcOp();

    protected ArcOp() {
        super();
    }

    @Override
    public void execute() {//x y r angle1 angle2 arc ---
        PSObject oAngle2 = runtime.popFromOperandStack();
        PSObject oAngle1 = runtime.popFromOperandStack();
        PSObject oR = runtime.popFromOperandStack();
        PSObject oY = runtime.popFromOperandStack();
        PSObject oX = runtime.popFromOperandStack();

        if ((oAngle2 == null || oAngle1 == null || oR == null || oY == null || oX == null) ||
                !(oAngle2.isNumber() && oAngle1.isNumber() && oR.isNumber() && oY.isNumber() && oX.isNumber())) {
            runtime.pushToOperandStack(oX);
            runtime.pushToOperandStack(oY);
            runtime.pushToOperandStack(oR);
            runtime.pushToOperandStack(oAngle1);
            runtime.pushToOperandStack(oAngle2);
            return;
        }
        double nAngle2 = ((PSNumber) oAngle2.getValue()).getRealValue();
        double nAngle1 = ((PSNumber) oAngle2.getValue()).getRealValue();
        double nR = ((PSNumber) oR.getValue()).getRealValue();
        double nY = ((PSNumber) oY.getValue()).getRealValue();
        double nX = ((PSNumber) oX.getValue()).getRealValue();

        PSPoint begining = new PSPoint(nX, nY);
        gState.currentPath.addArc(gState.currentPoint, begining, nR, nAngle1, nAngle2, false);
        double centerX = begining.getX();
        double centerY = begining.getY();
        gState.currentPoint = gState.currentPath.getLastSP().getEnd();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("arc");
    }
}
