package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.graphics.figures.PSPoint;

/**
 * Created by user on 15.03.14.
 */
public class RMoveToOp extends AbstractGraphicOperator {
    public static final RMoveToOp instance = new RMoveToOp();

    protected RMoveToOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject o1 = runtime.popFromOperandStack();
        PSObject o2 = runtime.popFromOperandStack();
        if ((o1 == null || o2 == null) || !(o1.isNumber() && o2.isNumber())) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            return;
        }
        PSNumber nY = (PSNumber) o1.getValue();
        PSNumber nX = (PSNumber) o2.getValue();
        PSPoint relCurPoint = gState.cTM.iTransform(gState.currentPoint);
        PSPoint p = gState.cTM.transform(nX.getRealValue() + relCurPoint.getX(), nY.getRealValue() + relCurPoint.getY());

        gState.currentPoint = gState.cTM.transform(p);
        gState.currentPath.getGeneralPath().moveTo(p.getX(), p.getY());

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rmoveto");
    }
}
