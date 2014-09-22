package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSReal;
import runtime.graphics.figures.PSPoint;

/**
 * Created by user on 24.03.14.
 */
public class CurrentPointOp extends AbstractGraphicOperator {
    public static final CurrentPointOp instance = new CurrentPointOp();

    protected CurrentPointOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getGState().currentPoint == null) {
            return;
        }
        PSPoint p = runtime.getGState().cTM.iTransform(runtime.getGState().currentPoint);
        runtime.pushToOperandStack(new PSObject(new PSReal(p.getX())));
        runtime.pushToOperandStack(new PSObject(new PSReal(p.getY())));

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("currentpoint");
    }
}
