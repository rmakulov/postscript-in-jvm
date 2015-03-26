package operators.coordinatSystemAndMatrix;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by user on 22.11.14.
 */
public class ConcatMatrixOp extends AbstractGraphicOperator {
    public static final ConcatMatrixOp instance = new ConcatMatrixOp();

    protected ConcatMatrixOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 3) {
            fail();
        }
        PSObject third = context.popFromOperandStack();
        PSObject second = context.popFromOperandStack();
        PSObject first = context.popFromOperandStack();

        if (first.isMatrix() && second.isMatrix() && third.isMatrix()) {
            TransformMatrix m1 = new TransformMatrix(first);
            TransformMatrix m3 = m1.multMatrix(second);
            PSArray newValue = (PSArray) m3.getMatrix().getValue();
            third.setValue(newValue);
            context.pushToOperandStack(third);
        } else {
            fail();
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("concatmatrix");
    }
}
