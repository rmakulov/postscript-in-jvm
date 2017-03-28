package operators.coordinatSystemAndMatrix;

import operators.AbstractGraphicOperator;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by user on 15.03.14.
 */
public class MatrixOp extends AbstractGraphicOperator {
    public static final MatrixOp instance = new MatrixOp();

    protected MatrixOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject matrix = new PSObject(TransformMatrix.getIdentityMatrix(), Attribute.TreatAs.LITERAL);
        context.pushToOperandStack(matrix);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("matrix");
    }
}
