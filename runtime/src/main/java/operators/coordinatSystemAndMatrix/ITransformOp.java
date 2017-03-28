package operators.coordinatSystemAndMatrix;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import psObjects.values.simple.numbers.PSReal;
import runtime.Context;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;

/**
 * Created by user on 16.03.14.
 */
public class ITransformOp extends AbstractGraphicOperator {
    public static final ITransformOp instance = new ITransformOp();

    protected ITransformOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject first = context.popFromOperandStack();
        PSObject s;
        //s.getType() == Type.ARRAY
        if (first == null) {
            return;
        }
        if (first.isNumber()) {
            PSObject oX = context.popFromOperandStack();
            if (oX == null || !oX.isNumber()) {
                context.pushToOperandStack(oX);
                context.pushToOperandStack(first);
                return;
            }
            double y = ((PSNumber) first.getValue()).getRealValue();
            double x = ((PSNumber) oX.getValue()).getRealValue();
            PSPoint res = context.getGState().cTM.iTransform(x, y);
            if (res == null) {
                context.pushToOperandStack(oX);
                context.pushToOperandStack(first);
                return;
            }
            context.pushToOperandStack(new PSObject(new PSReal(res.getX())));
            context.pushToOperandStack(new PSObject(new PSReal(res.getY())));
        } else if (first.getType() == Type.ARRAY && ((PSArray) first.getValue()).getArray().length == 6) {//x y matrix transform x' y'
            PSObject[] matrix = ((PSArray) first.getValue()).getArray();
            PSObject obj0 = matrix[0], obj1 = matrix[1], obj2 = matrix[2], obj3 = matrix[3], obj4 = matrix[4], obj5 = matrix[5];
            PSObject oY = context.popFromOperandStack();
            PSObject oX = context.popFromOperandStack();

            if (!(obj0.isNumber() && obj1.isNumber() && obj2.isNumber() && obj3.isNumber() && obj4.isNumber() && obj5.isNumber()
                    && oX.isNumber() && oY.isNumber())) {
                context.pushToOperandStack(oX);
                context.pushToOperandStack(oY);
                context.pushToOperandStack(first);
                return;
            }
            double nX = ((PSNumber) oX.getValue()).getRealValue();
            double nY = ((PSNumber) oY.getValue()).getRealValue();
            double[] newMatrix = new double[]{((PSNumber) obj0.getValue()).getRealValue(), ((PSNumber) obj1.getValue()).getRealValue(),
                    ((PSNumber) obj2.getValue()).getRealValue(), ((PSNumber) obj3.getValue()).getRealValue(),
                    ((PSNumber) obj4.getValue()).getRealValue(), ((PSNumber) obj5.getValue()).getRealValue()};
            TransformMatrix tM = new TransformMatrix(newMatrix);

            PSPoint res = tM.iTransform(nX, nY);
            if (res == null) {
                context.pushToOperandStack(oX);
                context.pushToOperandStack(oY);
                context.pushToOperandStack(first);
                return;
            }
            context.pushToOperandStack(new PSObject(new PSReal(res.getX())));
            context.pushToOperandStack(new PSObject(new PSReal(res.getY())));
        } else {
            context.pushToOperandStack(first);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("itransform");
    }
}
