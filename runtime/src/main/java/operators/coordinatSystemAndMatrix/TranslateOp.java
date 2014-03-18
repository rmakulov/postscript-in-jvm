package operators.coordinatSystemAndMatrix;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;

/**
 * Created by user on 15.03.14.
 */
public class TranslateOp extends AbstractGraphicOperator {
    public static final TranslateOp instance = new TranslateOp();

    protected TranslateOp() {
        super();
    }

    @Override
    public void execute() {//t_x t_y translate --
        if (runtime.getOperandStackSize() < 2) return;
        PSObject oTY = runtime.popFromOperandStack();
        PSObject oTX = runtime.popFromOperandStack();

        if (!(oTX.isNumber() && oTY.isNumber())) {
            runtime.pushToOperandStack(oTX);
            runtime.pushToOperandStack(oTY);
            return;
        }
        double t_y = ((PSNumber) (oTY.getValue())).getRealValue();
        double t_x = ((PSNumber) (oTX.getValue())).getRealValue();
        gState.cTM.translate(t_x, t_y);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("translate");
    }
}
