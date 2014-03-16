package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.graphics.point.PSPoint;

/**
 * Created by user on 15.03.14.
 */
public class RLineToOp extends AbstractGraphicOperator {
    @Override
    public void execute() { // x y rlineto ---
        PSObject o1 = runtime.popFromOperandStack();
        PSObject o2 = runtime.popFromOperandStack();
        if ((o1 == null || o2 == null) || !(o1.isNumber() && o2.isNumber())) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            return;
        }
        PSNumber nY = (PSNumber) o1.getValue();
        PSNumber nX = (PSNumber) o2.getValue();
        double newX = gState.currentPoint.getX() + nX.getRealValue() ;
        double newY = gState.currentPoint.getY() + nY.getRealValue() ;
        PSPoint newPoint = new PSPoint(newX, newY) ;
        gState.currentPath.addLineSegment(gState.currentPoint, newPoint) ;
        gState.currentPoint = newPoint ;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rlineto");
    }
}
