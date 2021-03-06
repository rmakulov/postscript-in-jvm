package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.Context;

import java.awt.*;

/**
 * Created by user on 15.03.14.
 */
public class SetRgbColorOp extends AbstractGraphicOperator {
    public static final SetRgbColorOp instance = new SetRgbColorOp();

    protected SetRgbColorOp() {
        super();
    }

    @Override
    public void interpret(Context context) {// red green blue setrgbcolor -
        PSObject oBlue = context.popFromOperandStack();
        PSObject oGreen = context.popFromOperandStack();
        PSObject oRed = context.popFromOperandStack();
        if (oBlue == null || oGreen == null || oRed == null ||
                !(oBlue.isNumber() && oGreen.isNumber() && oRed.isNumber())) {
            context.pushToOperandStack(oRed);
            context.pushToOperandStack(oGreen);
            context.pushToOperandStack(oBlue);
            return;
        }
        double nBlue = ((PSNumber) oBlue.getValue()).getRealValue();
        double nGreen = ((PSNumber) oGreen.getValue()).getRealValue();
        double nRed = ((PSNumber) oRed.getValue()).getRealValue();
        if (!(nBlue >= 0 && nBlue <= 1 && nGreen >= 0 && nGreen <= 1 && nRed >= 0 && nRed <= 1)) {
            return;
        }
        context.getGState().graphicsSettings.color = new Color((int) (255 * nRed), (int) (255 * nGreen), (int) (255 * nBlue));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setrgbcolor");
    }
}
