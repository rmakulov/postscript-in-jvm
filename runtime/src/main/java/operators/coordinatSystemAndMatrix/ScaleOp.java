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
public class ScaleOp extends AbstractGraphicOperator {
    public static final ScaleOp instance = new ScaleOp();

    protected ScaleOp() {
        super();
    }

    @Override
    public void interpret(Context context) {// s_x s_y scale --
        if (context.getOperandStackSize() < 2) {
            fail();
            return;
        }
        PSObject first = context.popFromOperandStack();
        PSObject second = context.popFromOperandStack();

        if (first.getType() == Type.ARRAY && context.getOperandStackSize() > 0) {
            PSObject third = context.popFromOperandStack();
            double t_y = ((PSNumber) (second.getValue())).getRealValue();
            double t_x = ((PSNumber) (third.getValue())).getRealValue();
            TransformMatrix m = new TransformMatrix(first);
            m.scale(t_x, t_y);
            context.pushToOperandStack(m.getMatrix());
        } else if (second.isNumber() && first.isNumber()) {
            double t_y = ((PSNumber) (first.getValue())).getRealValue();
            double t_x = ((PSNumber) (second.getValue())).getRealValue();
            context.getGState().cTM.scale(t_x, t_y);
        } else {
            fail();
        }
    }

    public void interpret(Context context, double sx, double sy){
        context.getGState().cTM.scale(sx, sy);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("scale");
    }
}
