package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class MarkOp extends Operator {
    public static final MarkOp instance = new MarkOp();

    protected MarkOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.pushToOperandStack(new PSObject(PSMark.OPEN_SQUARE_BRACKET));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("mark");
    }
}
