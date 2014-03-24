package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSReal;

/**
 * Created by user on 24.03.14.
 */
public class CurrentPointOp extends AbstractGraphicOperator {
    public static final CurrentPointOp instance = new CurrentPointOp();

    protected CurrentPointOp() {
        super();
    }

    @Override
    public void execute() {
        if (gState.currentPoint == null) {
            return;
        }
        runtime.pushToOperandStack(new PSObject(new PSReal(gState.currentPoint.getX())));
        runtime.pushToOperandStack(new PSObject(new PSReal(gState.currentPoint.getY())));

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("currentpoint");
    }
}
