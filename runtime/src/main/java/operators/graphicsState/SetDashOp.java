package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.Context;

/**
 * Created by Дмитрий on 27.03.14.
 */
public class SetDashOp extends AbstractGraphicOperator {
    public static final SetDashOp instance = new SetDashOp();

    protected SetDashOp() {
        super();
    }

    @Override
    public void interpret(Context context) { // -- gsave --
        if (context.getOperandStackSize() < 2) return;
        PSObject offsetObj = context.popFromOperandStack();
        PSObject arrayObj = context.popFromOperandStack();

        if (!offsetObj.isNumber() || arrayObj.getType() != Type.ARRAY) {
            context.pushToOperandStack(arrayObj);
            context.pushToOperandStack(offsetObj);
            fail();
            return;
        }
        PSArray psArray = (PSArray) arrayObj.getValue();
        PSObject[] psDashArr = psArray.getArray();
        float[] dashArr = new float[psDashArr.length];
        for (int i = 0; i < dashArr.length; i++) {
            if (!psDashArr[i].isNumber()) {
                context.pushToOperandStack(arrayObj);
                context.pushToOperandStack(offsetObj);
                return;
            }
            PSNumber num = (PSNumber) psDashArr[i].getValue();
            double dashElement = num.getRealValue();
            dashArr[i] = (float) dashElement;
        }
        if (dashArr.length == 0) {
            dashArr = null;
        }
        //gState.graphicsSettings.setDash(dashArr);
        context.getGState().graphicsSettings.dash = dashArr;

        PSNumber psDashPhase = (PSNumber) offsetObj.getValue();
        context.getGState().graphicsSettings.dashPhase = (float) psDashPhase.getRealValue();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setdash");
    }
}
