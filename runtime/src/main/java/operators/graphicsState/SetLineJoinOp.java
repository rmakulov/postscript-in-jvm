package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by user on 16.03.14.
 */
public class SetLineJoinOp extends AbstractGraphicOperator {
    public static final SetLineJoinOp instance = new SetLineJoinOp();

    protected SetLineJoinOp() {
        super();
    }

    @Override
    public void interpret(Context context) {// int setlinejoin Set shape of corners for setGraphicsSettings (0 = miter, 1 = round, 2 = bevel)
        PSObject oNum = context.popFromOperandStack();
        if (oNum == null || oNum.getType() != Type.INTEGER) {
            context.pushToOperandStack(oNum);
            return;
        }
        int num = ((PSInteger) oNum.getValue()).getIntValue();
        if (num < 0 || num > 2) {
            return;
        }
        context.getGState().graphicsSettings.lineJoin = num;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setlinejoin");
    }
}
