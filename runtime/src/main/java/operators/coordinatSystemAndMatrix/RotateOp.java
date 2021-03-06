package operators.coordinatSystemAndMatrix;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.Context;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by user on 15.03.14.
 */
public class RotateOp extends AbstractGraphicOperator {
    public static final RotateOp instance = new RotateOp();

    protected RotateOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) {
            fail();
            return;
        }
        PSObject first = context.popFromOperandStack();
        if (first.getType() == Type.ARRAY && context.getOperandStackSize() > 0) {
            PSObject second = context.popFromOperandStack();
            double oAngle = ((PSNumber) (second.getValue())).getRealValue();
            TransformMatrix m = new TransformMatrix(first);
            double newAngle = oAngle * Math.PI / 180;
            m.rotate(newAngle);
            context.pushToOperandStack(m.getMatrix());
        } else if (first.isNumber()) {
            double oAngle = ((PSNumber) (first.getValue())).getRealValue();
            double newAngle = oAngle * Math.PI / 180;
            context.getGState().cTM.rotate(newAngle);
        } else {
            fail();
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rotate");
    }
}
