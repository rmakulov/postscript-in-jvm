package operators.graphicStateDeviceIndependentOperators;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class CurrentFlatOp extends AbstractGraphicOperator {
    public static final CurrentFlatOp instance = new CurrentFlatOp();

    protected CurrentFlatOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.pushToOperandStack(new PSObject(new PSInteger(gState.graphicsSettings.flatness)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("currentflat");
    }
}
