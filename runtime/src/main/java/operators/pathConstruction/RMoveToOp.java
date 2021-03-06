package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.Context;
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
    public void interpret(Context context) {
        PSObject o1 = context.popFromOperandStack();
        PSObject o2 = context.popFromOperandStack();
        if ((o1 == null || o2 == null) || !(o1.isNumber() && o2.isNumber())) {
            context.pushToOperandStack(o2);
            context.pushToOperandStack(o1);
            return;
        }
        PSNumber nY = (PSNumber) o1.getValue();
        PSNumber nX = (PSNumber) o2.getValue();
        PSPoint relCurPoint = context.getGState().cTM.iTransform(context.getGState().currentPoint);
        PSPoint p = context.getGState().cTM.transform(nX.getRealValue() + relCurPoint.getX(), nY.getRealValue() + relCurPoint.getY());

        context.getGState().currentPoint = context.getGState().cTM.transform(p);
        context.getGState().currentPath.getGeneralPath().moveTo(p.getX(), p.getY());

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rmoveto");
    }
}
