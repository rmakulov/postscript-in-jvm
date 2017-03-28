package operators.coordinatSystemAndMatrix;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by User on 13/12/2014.
 */
public class InvertMatrixOp extends AbstractGraphicOperator {
    public static final InvertMatrixOp instance = new InvertMatrixOp();

    protected InvertMatrixOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) {
            fail();
        }
        PSObject second = context.popFromOperandStack();
        PSObject first = context.popFromOperandStack();

        if (first.isMatrix() && second.isMatrix()) {
            TransformMatrix m1 = new TransformMatrix(first);
            TransformMatrix m3 = m1.getInverseMatrix();
            PSArray newValue = (PSArray) m3.getMatrix().getValue();
            second.setValue(newValue);
            context.pushToOperandStack(second);
        } else {
            fail();
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("invertmatrix");
    }
}
