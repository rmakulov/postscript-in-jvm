package operators.array;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;

public class OpenSquareBracketOp extends Operator {

    @Override
    public void execute() {
        runtime.pushToOperandStack(new PSObject(PSMark.OPEN_SQUARE_BRACKET));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("[");
    }
}
