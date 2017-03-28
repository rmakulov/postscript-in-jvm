package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSReal;
import runtime.Context;
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
    public void interpret(Context context) {
        if (context.getGState().currentPoint == null) {
            return;
        }
        PSPoint p = context.getGState().cTM.iTransform(context.getGState().currentPoint);
        context.pushToOperandStack(new PSObject(new PSReal(p.getX())));
        context.pushToOperandStack(new PSObject(new PSReal(p.getY())));

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("currentpoint");
    }
}
