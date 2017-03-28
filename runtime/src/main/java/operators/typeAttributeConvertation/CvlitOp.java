package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;


public class CvlitOp extends Operator {

    public static final CvlitOp instance = new CvlitOp();

    protected CvlitOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        PSObject psObject = o.cvlit();
        if (psObject == null) {
            context.pushToOperandStack(o);
            return;
        }
        context.pushToOperandStack(psObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvlit");
    }
}
