package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class CvxOp extends Operator {

    public static final CvxOp instance = new CvxOp();

    protected CvxOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
//        if (o.getType() == Type.ARRAY && runtime.isCompiling) {
//            runtime.bcGenManager.startCodeGenerator();
//            PSArray psArray = ((PSArray) o.getValue());
//            for (PSObject psObject : psArray.getArray()) {
//                psObject.compile();
//            }
//            runtime.bcGenManager.endBytecode();
//            runtime.pushToOperandStack(new PSObject(runtime.bcGenManager.getCur(), Attribute.TreatAs.EXECUTABLE));
//        } else {
            runtime.pushToOperandStack(o.cvx());
//        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvx");
    }
}
