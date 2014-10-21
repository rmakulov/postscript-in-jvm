package operators.control;

import procedures.ArrayProcedure;
import procedures.StringProcedure;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

import java.io.UnsupportedEncodingException;

import static psObjects.Attribute.TreatAs;

public class ExecOp extends Operator {
    public static final ExecOp instance = new ExecOp();

    protected ExecOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null) {
            return;
        }
        if (psObject.isProc()) {
            if (!runtime.isCompiling) {
                runtime.pushToCallStack(new ArrayProcedure("Exec procedure", psObject));
            } else {
                fail();
            }
        } else if (psObject.getType() == Type.STRING && psObject.xcheck()) {
            try {
                runtime.pushToCallStack(new StringProcedure(psObject));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            if (runtime.isCompiling) {
                psObject.execute(0);
            } else {
                PSObject[] singleArray = new PSObject[]{psObject};
                PSObject psExecArray = new PSObject(new PSArray(singleArray), TreatAs.EXECUTABLE);
                runtime.pushToCallStack(new ArrayProcedure("Exec procedure", psExecArray));
            }
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exec");
    }
}
