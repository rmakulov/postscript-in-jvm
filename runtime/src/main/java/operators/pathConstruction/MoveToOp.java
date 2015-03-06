package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
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
    public void interpret() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject o1 = runtime.popFromOperandStack();
        PSObject o2 = runtime.popFromOperandStack();
        if (!(o1.isNumber() && o2.isNumber())) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            fail();
        }
        PSNumber nY = (PSNumber) o1.getValue();
        PSNumber nX = (PSNumber) o2.getValue();
        PSPoint psPoint = runtime.getGState().cTM.transform(nX.getRealValue(), nY.getRealValue());
        runtime.getGState().currentPoint = psPoint;
        runtime.getGState().currentPath.getGeneralPath().moveTo(psPoint.getX(), psPoint.getY());

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("moveto");
    }
}
