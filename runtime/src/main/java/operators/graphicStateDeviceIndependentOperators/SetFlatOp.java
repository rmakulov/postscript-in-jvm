package operators.graphicStateDeviceIndependentOperators;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class SetFlatOp extends AbstractGraphicOperator {
    public static final SetFlatOp instance = new SetFlatOp();

    protected SetFlatOp() {
        super();
    }

    @Override
    public void interpret(Context context) {//todo check errors with stack and type
        if (context.getOperandStackSize() < 1) {
            return;
        }
        PSObject o = context.popFromOperandStack();
        if (o.getType() != Type.INTEGER) {
            context.pushToOperandStack(o);
            return;
        }
        PSInteger psInteger = (PSInteger) o.getValue();
        context.getGState().graphicsSettings.flatness = psInteger.getIntValue();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setflat");
    }
}
