package operators.typeAttributeConvertation;

import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

public class CvxOp extends Operator {

    public static final CvxOp instance = new CvxOp();

    protected CvxOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject o = context.popFromOperandStack();
        if (o == null) return;
        if (o.getType() == Type.ARRAY && runtime.isCompiling) {
            runtime.bcGenManager.startCodeGenerator();
            PSArray psArray = ((PSArray) o.getValue());
            for (PSObject psObject : psArray.getArray()) {
                psObject.compile(context);
            }
            runtime.bcGenManager.endBytecode();
            context.pushToOperandStack(new PSObject(runtime.bcGenManager.getCur(), Attribute.TreatAs.EXECUTABLE));
        } else {
            context.pushToOperandStack(o.cvx());
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvx");
    }
}
