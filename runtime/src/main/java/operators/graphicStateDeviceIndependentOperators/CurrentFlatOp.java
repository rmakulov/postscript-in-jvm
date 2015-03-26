package operators.graphicStateDeviceIndependentOperators;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class CurrentFlatOp extends AbstractGraphicOperator {
    public static final CurrentFlatOp instance = new CurrentFlatOp();

    protected CurrentFlatOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.pushToOperandStack(new PSObject(new PSInteger(context.getGState().graphicsSettings.flatness)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("currentflat");
    }
}
