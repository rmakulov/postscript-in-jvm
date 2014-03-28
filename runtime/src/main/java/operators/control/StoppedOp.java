package operators.control;

import procedures.StoppedArrayProcedure;
import procedures.StoppedStringProcedure;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class StoppedOp extends Operator {
    public static final StoppedOp instance = new StoppedOp();

    protected StoppedOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null) {
            return;
        }
        if (psObject.isProc()) {
            runtime.pushToCallStack(new StoppedArrayProcedure(psObject));
        } else if (psObject.getType() == Type.STRING && psObject.xcheck()) {
            runtime.pushToCallStack(new StoppedStringProcedure(psObject));
        } else {
            PSObject[] singleArray = new PSObject[]{psObject};
            PSObject psExecArray = new PSObject(new PSArray(singleArray), Attribute.TreatAs.EXECUTABLE);
            runtime.pushToCallStack(new StoppedArrayProcedure(psExecArray));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("stopped");
    }
}
