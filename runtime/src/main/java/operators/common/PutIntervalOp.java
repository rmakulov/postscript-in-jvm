package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

public class PutIntervalOp extends Operator {

    public static final PutIntervalOp instance = new PutIntervalOp();

    protected PutIntervalOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject src = runtime.popFromOperandStack();
        if (src == null)
            return;

        PSObject index = runtime.popFromOperandStack();
        if (index == null) {
            runtime.pushToOperandStack(src);
            return;
        }

        PSObject dst = runtime.popFromOperandStack();
        if (dst == null || index.getType() != Type.INTEGER) {
            runtime.pushToOperandStack(index);
            runtime.pushToOperandStack(src);
            return;
        }

        int start = ((PSInteger) index.getValue()).getIntValue();
        Value result;
        if (dst.getType() == Type.ARRAY && (src.getType() == Type.ARRAY || src.getType() == Type.PACKEDARRAY)) {
            PSArray dstArray = (PSArray) dst.getValue();
            PSArray srcArray = (PSArray) src.getValue();
            dst.setValue(dstArray.putInterval(start, srcArray));
        } else if (dst.getType() == Type.STRING && src.getType() == Type.STRING) {
            PSString dstString = (PSString) dst.getValue();
            PSString srcString = (PSString) src.getValue();
            //todo right
            dst.setValue((dstString).setSubstring(start, srcString.getString()));
        } else {
            runtime.pushToOperandStack(dst);
            runtime.pushToOperandStack(index);
            runtime.pushToOperandStack(src);
            return;
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("putinterval");
    }
}
