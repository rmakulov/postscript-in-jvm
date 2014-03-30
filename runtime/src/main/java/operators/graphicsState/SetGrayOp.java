package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;

import java.awt.*;

/**
 * Created by user on 15.03.14.
 */
public class SetGrayOp extends AbstractGraphicOperator {
    public static final SetGrayOp instance = new SetGrayOp();

    protected SetGrayOp() {
        super();
    }

    @Override
    public void execute() { //num setgray setgray --
        PSObject oNum = runtime.popFromOperandStack();
        if (oNum == null || !(oNum.isNumber())) {
            runtime.pushToOperandStack(oNum);
            return;
        }
        double grayScale = ((PSNumber) oNum.getValue()).getRealValue();
        if (grayScale < 0) grayScale = 0;
        if (grayScale > 1) grayScale = 1;
        int col = (int) (255 * grayScale);
        gState.graphicsSettings.color = new Color(col, col, col, 255);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setgray");
    }
}
