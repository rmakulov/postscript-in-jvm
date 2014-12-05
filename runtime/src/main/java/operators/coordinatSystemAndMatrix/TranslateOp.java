package operators.coordinatSystemAndMatrix;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by user on 15.03.14.
 */
public class TranslateOp extends AbstractGraphicOperator {
    public static final TranslateOp instance = new TranslateOp();

    protected TranslateOp() {
        super();
    }

    @Override
    public void interpret() {//t_x t_y translate --
        if (runtime.getOperandStackSize() < 2) {
            fail();
            return;
        }

        PSObject first = runtime.popFromOperandStack();
        PSObject second = runtime.popFromOperandStack();

        if (first.getType() == Type.ARRAY && runtime.getOperandStackSize() > 0) {
            PSObject third = runtime.popFromOperandStack();
            double t_y = ((PSNumber) (second.getValue())).getRealValue();
            double t_x = ((PSNumber) (third.getValue())).getRealValue();
            TransformMatrix m = new TransformMatrix(first);
            m.translate(t_x, t_y);
            runtime.pushToOperandStack(m.getMatrix());
        } else if (second.isNumber() && first.isNumber()) {
            double t_y = ((PSNumber) (first.getValue())).getRealValue();
            double t_x = ((PSNumber) (second.getValue())).getRealValue();
            runtime.getGState().cTM.translate(t_x, t_y);
        } else {
            fail();
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("translate");
    }
}
