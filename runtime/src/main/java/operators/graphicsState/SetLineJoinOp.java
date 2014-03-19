package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by user on 16.03.14.
 */
public class SetLineJoinOp extends AbstractGraphicOperator {
    public static final SetLineJoinOp instance = new SetLineJoinOp();

    protected SetLineJoinOp() {
        super();
    }

    @Override
    public void execute() {// int setlinejoin Set shape of corners for setPaintingStateOfLastSequentialPath (0 = miter, 1 = round, 2 = bevel)
        PSObject oNum = runtime.popFromOperandStack();
        if (oNum == null || oNum.getType() != Type.INTEGER) {
            runtime.pushToOperandStack(oNum);
            return;
        }
        int num = ((PSInteger) oNum.getValue()).getIntValue();
        if (num < 0 || num > 2) {
            return;
        }
        gState.graphicsSettings.lineJoin = num;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setlinejoin");
    }
}
