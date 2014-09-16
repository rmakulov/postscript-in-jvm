package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.graphics.figures.PSPoint;

/**
 * Created by user on 15.03.14.
 */
public class RLineToOp extends AbstractGraphicOperator {
    public static final RLineToOp instance = new RLineToOp();

    protected RLineToOp() {
        super();
    }

    @Override
    public void interpret() { // x y rlineto ---
        PSObject o1 = runtime.popFromOperandStack();
        PSObject o2 = runtime.popFromOperandStack();
        if ((o1 == null || o2 == null) || !(o1.isNumber() && o2.isNumber())) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            return;
        }
        PSPoint relCurPoint = gState.cTM.iTransform(gState.currentPoint);
        PSNumber nY = (PSNumber) o1.getValue();
        PSNumber nX = (PSNumber) o2.getValue();
        PSPoint p = gState.cTM.transform(nX.getRealValue() + relCurPoint.getX(),
                nY.getRealValue() + relCurPoint.getY());

        //double curX = gState.currentPoint.getX();
        //double curY = gState.currentPoint.getY();

        PSPoint newPoint = new PSPoint(p.getX(), p.getY());
        gState.currentPath.addLine(gState.currentPoint, newPoint);
        gState.currentPoint = newPoint;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rlineto");
    }
}
