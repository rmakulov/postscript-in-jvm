package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;


public class CvlitOp extends Operator {

    public static final CvlitOp instance = new CvlitOp();

    protected CvlitOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        PSObject psObject = o.cvlit();
        if (psObject == null) {
            runtime.pushToOperandStack(o);
            return;
        }
        runtime.pushToOperandStack(psObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvlit");
    }
}
