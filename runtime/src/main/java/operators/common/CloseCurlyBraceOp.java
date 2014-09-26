package operators.common;

import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.Value;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import runtime.Runtime;

import java.util.ArrayList;

public class CloseCurlyBraceOp extends Operator {
    public static final CloseCurlyBraceOp instance = new CloseCurlyBraceOp();

    protected CloseCurlyBraceOp() {
        super();
    }

    @Override
    public void interpret() {

        if (runtime.isCompiling && !runtime.bcGen.isSleep()) {
            runtime.bcGen.endBytecode();
            runtime.pushToOperandStack(new PSObject(runtime.bcGen.getCur()));
        } else {
            //runtime.pushToOperandStack(new PSObject(PSMark.CLOSE_CURLY_BRACE));
            ArrayList<PSObject> gatherArray = gatherArray();
            if (gatherArray == null) {
                return;
            }
            PSArray result = new PSArray(gatherArray);
            runtime.pushToOperandStack(new PSObject(result, Attribute.TreatAs.EXECUTABLE));
        }
    }


    public static void compile() {
        runtime.Runtime runtime = Runtime.getInstance();
        //todo right
        (runtime.bcGen.getCur()).compile(null);

        //runtime.pushToOperandStack(runtime.bcGen.getCur());
       /* runtime.bcGen.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGen.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGen.mv.visitFieldInsn(GETFIELD, "runtime/Runtime", "bcGen", "Lruntime/BytecodeGeneratorManager;");
        runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/BytecodeGeneratorManager", "getCur", "()LpsObjects/PSObject;", false);
        runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);*/
    }

    private ArrayList<PSObject> gatherArray() {
        if (runtime.getOperandStackSize() < 1) return null;
        PSObject psObject = runtime.popFromOperandStack();
        ArrayList<PSObject> array = new ArrayList<PSObject>();
        int procDepth = 1;
        while (!PSMark.OPEN_CURLY_BRACE.equals(psObject.getValue()) || procDepth > 0) {
            array.add(psObject);
            psObject = runtime.popFromOperandStack();
            if (psObject == null) {
                return null;
            }
            Value psValue = psObject.getValue();
            if (PSMark.OPEN_CURLY_BRACE.equals(psValue)) {
                procDepth--;
            } else if (PSMark.CLOSE_CURLY_BRACE.equals(psValue)) {
                procDepth++;
            }
        }
        ArrayList<PSObject> result = new ArrayList<PSObject>();
        for (int i = 0; i < array.size(); i++) {
            result.add(array.get(array.size() - i - 1));
        }
        return result;
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("}");
    }
}
