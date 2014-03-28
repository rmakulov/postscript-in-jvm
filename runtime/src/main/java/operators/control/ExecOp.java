package operators.control;

import procedures.ArrayProcedure;
import procedures.StringProcedure;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

import static psObjects.Attribute.TreatAs;

public class ExecOp extends Operator {
    public static final ExecOp instance = new ExecOp();

    protected ExecOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null) {
            return;
        }
        if (psObject.isProc()) {
            runtime.pushToCallStack(new ArrayProcedure("Exec procedure", psObject));
        } else if (psObject.getType() == Type.STRING && psObject.xcheck()) {
            runtime.pushToCallStack(new StringProcedure(psObject));
        } else {
            PSObject[] singleArray = new PSObject[]{psObject};
            PSObject psExecArray = new PSObject(new PSArray(singleArray), TreatAs.EXECUTABLE);
            runtime.pushToCallStack(new ArrayProcedure("Exec procedure", psExecArray));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exec");
    }
}
