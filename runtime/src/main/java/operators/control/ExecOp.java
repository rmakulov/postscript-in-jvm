package operators.control;

import procedures.ArrayProcedure;
import psObjects.PSObject;
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
    public boolean interpret(PSObject obj) {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null) {
            return true;
        }
        if (psObject.isProc()) {
            if (!runtime.isCompiling) {
                runtime.pushToCallStack(new ArrayProcedure("Exec procedure", psObject));
            } else {
                fail();
            }
        } else if (psObject.isExecutableString()) {
            return psObject.interpret(0);
        } else if (psObject.isFile()) {
            // do nothing
            runtime.pushToOperandStack(psObject);
        } else {
            if (runtime.isCompiling) {
                return psObject.execute(0);
            } else {
                PSObject[] singleArray = new PSObject[]{psObject};
                PSObject psExecArray = new PSObject(new PSArray(singleArray), TreatAs.EXECUTABLE);
                runtime.pushToCallStack(new ArrayProcedure("Exec procedure", psExecArray));
            }
        }
        return true;
    }

    @Override
    public void interpret() {
        //nothing to do
        try {
            throw new Exception("Wrong method invocation");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //    @Override
//    public void interpret() {
//        PSObject psObject = runtime.popFromOperandStack();
//        if (psObject == null) {
//            return;
//        }
//        if (psObject.isProc()) {
//            if (!runtime.isCompiling) {
//                runtime.pushToCallStack(new ArrayProcedure("Exec procedure", psObject));
//            } else {
//                fail();
//            }
//        } else if (psObject.getType() == Type.STRING && psObject.xcheck()) {
//            psObject.interpret(0);
//        } else {
//            if (runtime.isCompiling) {
//                psObject.execute(0);
//            } else {
//                PSObject[] singleArray = new PSObject[]{psObject};
//                PSObject psExecArray = new PSObject(new PSArray(singleArray), TreatAs.EXECUTABLE);
//                runtime.pushToCallStack(new ArrayProcedure("Exec procedure", psExecArray));
//            }
//        }
//    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exec");
    }
}
