package operators.coordinatSystemAndMatrix;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSReal;
import runtime.Context;
import runtime.graphics.GState;

/**
 * Created by user on 5/19/15.
 */
public class CurrentMatrixOp extends AbstractGraphicOperator {
    public static final CurrentMatrixOp instance = new CurrentMatrixOp();

    protected CurrentMatrixOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1){
            fail();
        }
        PSObject obj = context.getOperandStack().pop();
        if(obj.getType() != Type.ARRAY || ((PSArray) obj.getValue()).getArray().length != 6){
            context.pushToOperandStack(obj);
            fail();
            return;
        }
        PSObject[] arr = ((PSArray) obj.getValue()).getArray();
        double[] dArr = context.getGState().cTM.getDoubleArray();
        for(int i = 0; i < 6 ; i++) {
            arr[i] = new PSObject(new PSReal(dArr[i]));
        }
        context.pushToOperandStack(new PSObject(new PSArray(arr)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("currentmatrix");
    }
}
