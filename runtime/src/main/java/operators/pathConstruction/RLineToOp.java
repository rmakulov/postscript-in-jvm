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
public class RLineToOp extends AbstractGraphicOperator {
    public static final RLineToOp instance = new RLineToOp();

    protected RLineToOp() {
        super();
    }

    @Override
    public void interpret(Context context) { // x y rlineto ---
        PSObject o1 = context.popFromOperandStack();
        PSObject o2 = context.popFromOperandStack();
        if ((o1 == null || o2 == null) || !(o1.isNumber() && o2.isNumber())) {
            context.pushToOperandStack(o2);
            context.pushToOperandStack(o1);
            return;
        }
        PSPoint relCurPoint = context.getGState().cTM.iTransform(context.getGState().currentPoint);
        PSNumber nY = (PSNumber) o1.getValue();
        PSNumber nX = (PSNumber) o2.getValue();
        PSPoint p = context.getGState().cTM.transform(nX.getRealValue() + relCurPoint.getX(),
                nY.getRealValue() + relCurPoint.getY());

        //double curX = gState.currentPoint.getX();
        //double curY = gState.currentPoint.getY();

        PSPoint newPoint = new PSPoint(p.getX(), p.getY());
        context.getGState().currentPath.addLine(context.getGState().currentPoint, newPoint);
        context.getGState().currentPoint = newPoint;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rlineto");
    }
}
