package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSNumber;

/**
 * Created by Дмитрий on 27.03.14.
 */
public class SetDashOp extends AbstractGraphicOperator {
    public static final SetDashOp instance = new SetDashOp();

    protected SetDashOp() {
        super();
    }

    @Override
    public void execute() { // -- gsave --
        if (runtime.getOperandStackSize() < 2) return;
        PSObject offsetObj = runtime.popFromOperandStack();
        PSObject arrayObj = runtime.popFromOperandStack();

        if (offsetObj.getType() != Type.INTEGER || arrayObj.getType() != Type.ARRAY) {
            runtime.pushToOperandStack(arrayObj);
            runtime.pushToOperandStack(offsetObj);
            return;
        }
        PSArray psArray = (PSArray) arrayObj.getValue();
        PSObject[] psDashArr = psArray.getArray();
        float[] dashArr = new float[psDashArr.length];
        for (int i = 0; i < dashArr.length; i++) {
            if (!psDashArr[i].isNumber()) {
                runtime.pushToOperandStack(arrayObj);
                runtime.pushToOperandStack(offsetObj);
                return;
            }
            PSNumber num = (PSNumber) psDashArr[i].getValue();
            double dashElement = num.getRealValue();
            dashArr[i] = (float) dashElement;
        }
        if (dashArr.length == 0) {
            dashArr = null;
        }
        gState.graphicsSettings.dash = dashArr;

        PSInteger psDashPhase = (PSInteger) offsetObj.getValue();
        gState.graphicsSettings.dashPhase = psDashPhase.getIntValue();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setdash");
    }
}
