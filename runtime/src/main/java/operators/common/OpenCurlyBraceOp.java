package operators.common;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;

public class OpenCurlyBraceOp extends Operator {
    @Override
    public void execute() {
        runtime.pushToOperandStack(new PSObject(PSMark.OPEN_CURLY_BRACE));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("{");
    }
}