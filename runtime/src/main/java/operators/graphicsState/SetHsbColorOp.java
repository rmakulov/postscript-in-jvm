package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;

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
    public void interpret() { // hue saturation brightness sethsbcolor --
        PSObject oBright = runtime.popFromOperandStack();
        PSObject oSatur = runtime.popFromOperandStack();
        PSObject oHue = runtime.popFromOperandStack();

        if (oBright == null || oSatur == null || oHue == null ||
                !(oBright.isNumber() && oSatur.isNumber() && oHue.isNumber())) {
            runtime.pushToOperandStack(oHue);
            runtime.pushToOperandStack(oSatur);
            runtime.pushToOperandStack(oBright);
            return;
        }

        float nBright = (float) ((PSNumber) oBright.getValue()).getRealValue();
        float nSatur = (float) ((PSNumber) oSatur.getValue()).getRealValue();
        float nHue = (float) ((PSNumber) oHue.getValue()).getRealValue();
        if (!(nBright >= 0 && nBright <= 1 && nSatur >= 0 && nSatur <= 1 && nHue >= 0 && nHue <= 1)) {
            return;
        }
        runtime.getGState().graphicsSettings.color = Color.getHSBColor((float) nHue, (float) nSatur, (float) nBright);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("sethsbcolor");
    }
}
