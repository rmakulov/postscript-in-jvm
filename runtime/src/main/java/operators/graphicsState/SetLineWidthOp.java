package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;
import runtime.Context;

/**
 * Created by user on 15.03.14.
 */
public class SetLineWidthOp extends AbstractGraphicOperator {
    public static final SetLineWidthOp instance = new SetLineWidthOp();

    protected SetLineWidthOp() {
        super();
    }

    @Override
    public void interpret(Context context) {// num setlinewidth -
        PSObject oNum = context.popFromOperandStack();
        if (oNum == null || !(oNum.isNumber())) {
            context.pushToOperandStack(oNum);
            return;
        }
        double XScale = context.getGState().cTM.getXScale();
        double YScale = context.getGState().cTM.getXScale();
        double scale = Math.sqrt(XScale * YScale);
        double width = ((PSNumber) oNum.getValue()).getRealValue();

        context.getGState().graphicsSettings.lineWidth = width * scale;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setlinewidth");
    }
}
