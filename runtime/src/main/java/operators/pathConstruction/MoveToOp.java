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
public class MoveToOp extends AbstractGraphicOperator {
    public static final MoveToOp instance = new MoveToOp();

    protected MoveToOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject o1 = context.popFromOperandStack();
        PSObject o2 = context.popFromOperandStack();
        if (!(o1.isNumber() && o2.isNumber())) {
            context.pushToOperandStack(o2);
            context.pushToOperandStack(o1);
            fail();
        }
        PSNumber nY = (PSNumber) o1.getValue();
        PSNumber nX = (PSNumber) o2.getValue();
        PSPoint psPoint = context.getGState().cTM.transform(nX.getRealValue(), nY.getRealValue());
        context.getGState().currentPoint = psPoint;
        context.getGState().currentPath.getGeneralPath().moveTo(psPoint.getX(), psPoint.getY());

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("moveto");
    }
}
