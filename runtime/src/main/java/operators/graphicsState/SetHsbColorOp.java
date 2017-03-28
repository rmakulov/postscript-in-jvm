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
public class SetHsbColorOp extends AbstractGraphicOperator {
    public static final SetHsbColorOp instance = new SetHsbColorOp();

    protected SetHsbColorOp() {
        super();
    }

    @Override
    public void interpret(Context context) { // hue saturation brightness sethsbcolor --
        PSObject oBright = context.popFromOperandStack();
        PSObject oSatur = context.popFromOperandStack();
        PSObject oHue = context.popFromOperandStack();

        if (oBright == null || oSatur == null || oHue == null ||
                !(oBright.isNumber() && oSatur.isNumber() && oHue.isNumber())) {
            context.pushToOperandStack(oHue);
            context.pushToOperandStack(oSatur);
            context.pushToOperandStack(oBright);
            return;
        }

        float nBright = (float) ((PSNumber) oBright.getValue()).getRealValue();
        float nSatur = (float) ((PSNumber) oSatur.getValue()).getRealValue();
        float nHue = (float) ((PSNumber) oHue.getValue()).getRealValue();
        if (!(nBright >= 0 && nBright <= 1 && nSatur >= 0 && nSatur <= 1 && nHue >= 0 && nHue <= 1)) {
            return;
        }
        context.getGState().graphicsSettings.color = Color.getHSBColor((float) nHue, (float) nSatur, (float) nBright);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("sethsbcolor");
    }
}
