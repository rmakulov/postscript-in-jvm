package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.graphics.point.PSPoint;

/**
 * Created by user on 15.03.14.
 */
public class MoveToOp extends AbstractGraphicOperator {

    @Override
    public void execute() {
        PSObject o1 = runtime.popFromOperandStack();
        PSObject o2 = runtime.popFromOperandStack();
        if ((o1 == null || o2 == null) || !(o1.isNumber() && o2.isNumber())) {
            runtime.pushToOperandStack(o2);
            runtime.pushToOperandStack(o1);
            return;
        }
        PSNumber nY = (PSNumber) o1.getValue();
        PSNumber nX = (PSNumber) o2.getValue();
        gState.currentPoint = new PSPoint(nX.getRealValue(), nY.getRealValue());
        //runtime.pushToOperandStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("moveto");
    }
}
