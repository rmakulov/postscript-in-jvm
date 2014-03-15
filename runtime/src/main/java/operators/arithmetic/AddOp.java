package operators.arithmetic;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.Operator;
import runtime.Runtime;

public class AddOp extends Operator {
    @Override
    public void execute() {
        Runtime runtime = Runtime.getInstance();
        PSObject o1 = runtime.popFromOperandStack();
        PSObject o2 = runtime.popFromOperandStack();
        if (o1 != null && o2 != null) {
            if (o1.getType() == Type.INTEGER && o2.getType() == Type.INTEGER) {
                PSInteger i1 = (PSInteger) o1.getValue();
                PSInteger i2 = (PSInteger) o2.getValue();
                PSInteger iSum = PSInteger.add(i1, i2);
                runtime.pushToOperandStack(new PSObject(iSum));
            }
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("add");
    }
}
