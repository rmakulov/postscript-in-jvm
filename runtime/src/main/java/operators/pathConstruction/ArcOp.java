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
        //todo
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

        double xBegin = nX + nR * Math.cos(nAngle1 * Math.PI / 180);
        double yBegin = nY + nR * Math.sin(nAngle1 * Math.PI / 180);
        double xEnd = nX + nR * Math.cos(nAngle2 * Math.PI / 180);
        double yEnd = nY + nR * Math.sin(nAngle2 * Math.PI / 180);

        PSPoint absBegin = gState.cTM.transform(xBegin, yBegin);
        PSPoint absEnd = gState.cTM.transform(xEnd, yEnd);

        PSPoint begining = new PSPoint(nX, nY);
        gState.currentPath.addArc(absBegin, absEnd, begining, nR, nAngle1, nAngle2, false, gState.cTM);
        gState.currentPoint = gState.currentPath.getLastSP().getEnd();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("arc");
    }
}
