package operators.common;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;

public class OpenCurlyBraceOp extends Operator {
    public static final OpenCurlyBraceOp instance = new OpenCurlyBraceOp();

    protected OpenCurlyBraceOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.isCompiling) {
            runtime.bcGenManager.startCodeGenerator();
        } else {
            runtime.pushToOperandStack(new PSObject(PSMark.OPEN_CURLY_BRACE));
        }
        //runtime.pushToOperandStack(new PSObject(PSMark.OPEN_CURLY_BRACE));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("{");
    }
}
