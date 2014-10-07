package operators.coordinatSystemAndMatrix;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.PSName;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by user on 26.09.14.
 */
public class ConcatOp extends AbstractGraphicOperator {
    public static final ConcatOp instance = new ConcatOp();

    protected ConcatOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject psArray = runtime.popFromOperandStack();
        if (psArray == null || psArray.getType() != Type.ARRAY) {
            fail();
            runtime.pushToOperandStack(psArray);
            return;
        }
        PSArray arr = ((PSArray) psArray.getValue());
        if (arr.size() != 6) {
            fail();
            runtime.pushToOperandStack(psArray);
            return;
        }
        TransformMatrix cTM = runtime.getGState().cTM;
        TransformMatrix transformMatrix = new TransformMatrix(psArray).multMatrix(cTM.getMatrix());
        cTM.setMatrix(transformMatrix.getMatrix());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("concat");
    }
}
