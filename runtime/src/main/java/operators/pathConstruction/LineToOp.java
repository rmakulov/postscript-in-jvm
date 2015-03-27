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
public class LineToOp extends AbstractGraphicOperator {
    public static final LineToOp instance = new LineToOp();

    protected LineToOp() {
        super();
    }

    @Override
    public void interpret(Context context) {// x y lineto ---
        PSObject o1 = context.popFromOperandStack();
        PSObject o2 = context.popFromOperandStack();
        if ((o1 == null || o2 == null) || !(o1.isNumber() && o2.isNumber())) {
            context.pushToOperandStack(o2);
            context.pushToOperandStack(o1);

            return;
        }
        PSNumber nY = (PSNumber) o1.getValue();
        PSNumber nX = (PSNumber) o2.getValue();

        PSPoint newPoint = context.getGState().cTM.transform(nX.getRealValue(), nY.getRealValue());
        context.getGState().currentPath.addLine(context.getGState().currentPoint, newPoint);
        context.getGState().currentPoint = newPoint;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("lineto");
    }
}
