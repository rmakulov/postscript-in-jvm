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
    public void execute() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject o1 = runtime.popFromOperandStack();
        PSObject o2 = runtime.popFromOperandStack();
        if (!(o1.isNumber() && o2.isNumber())) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            return;
        }
        PSNumber nY = (PSNumber) o1.getValue();
        PSNumber nX = (PSNumber) o2.getValue();
        PSPoint psPoint = gState.cTM.transform(nX.getRealValue(), nY.getRealValue());
        gState.currentPoint = psPoint;
        gState.currentPath.getLastGeneralPath().moveTo(psPoint.getX(), psPoint.getY());

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("moveto");
    }
}
