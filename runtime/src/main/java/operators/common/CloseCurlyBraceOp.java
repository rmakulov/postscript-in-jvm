package operators.common;

import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.Value;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;

import java.util.ArrayList;

public class CloseCurlyBraceOp extends Operator {
    public static final CloseCurlyBraceOp instance = new CloseCurlyBraceOp();

    protected CloseCurlyBraceOp() {
        super();
    }

    @Override
    public void interpret() {

//        if (runtime.isCompiling && !runtime.bcGenManager.isSleep()) {
//            runtime.bcGenManager.endBytecode();
//            PSBytecode originBytecode = runtime.bcGenManager.getCur();
//            BytecodeProc bytecodeProc = new BytecodeProc(originBytecode);
//            runtime.pushToOperandStack(new PSObject(bytecodeProc));
//        } else {
            //runtime.pushToOperandStack(new PSObject(PSMark.CLOSE_CURLY_BRACE));
            ArrayList<PSObject> gatherArray = gatherArray();
            if (gatherArray == null) {
                return;
            }
            PSArray result = new PSArray(gatherArray);
            runtime.pushToOperandStack(new PSObject(result, Attribute.TreatAs.EXECUTABLE));
//        }
    }


    public static void compile() {
        try {
            throw new Exception("Warning! CloseCurlyBraceOp compilation attempt.");
        } catch (Exception e) {
            e.printStackTrace();
        }
////        CloseCurlyBraceOp.instance.interpret();
//        runtime.Runtime runtime = Runtime.getInstance();
//        MethodVisitor mv = runtime.bcGenManager.mv;
//        mv.visitFieldInsn(GETSTATIC, "operators/common/CloseCurlyBraceOp", "instance", "Loperators/common/CloseCurlyBraceOp;");
//        mv.visitMethodInsn(INVOKEVIRTUAL, "operators/common/CloseCurlyBraceOp", "interpret", "()V", false);

        //runtime.pushToOperandStack(runtime.bcGenManager.getCur());
       /* runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGenManager.mv.visitFieldInsn(GETFIELD, "runtime/Runtime", "bcGenManager", "Lruntime/BytecodeGeneratorManager;");
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/BytecodeGeneratorManager", "getCur", "()LpsObjects/PSObject;", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);*/
    }

//    private ArrayList<PSObject> gatherArray() {
//        if (runtime.getOperandStackSize() < 1) return null;
//        PSObject psObject = runtime.popFromOperandStack();
//        ArrayList<PSObject> array = new ArrayList<PSObject>();
//        int procDepth = 1;
//        Value psValue = psObject.getValue();
//        if (PSMark.OPEN_CURLY_BRACE.equals(psValue)) {
//            procDepth--;
//        } else if (PSMark.CLOSE_CURLY_BRACE.equals(psValue)) {
//            procDepth++;
//        }
//        while (!PSMark.OPEN_CURLY_BRACE.equals(psObject.getValue()) || procDepth > 0) {
//            array.add(psObject);
//            psObject = runtime.popFromOperandStack();
//            if (psObject == null) {
//                return null;
//            }
//            psValue = psObject.getValue();
//            if (PSMark.OPEN_CURLY_BRACE.equals(psValue)) {
//                procDepth--;
//            } else if (PSMark.CLOSE_CURLY_BRACE.equals(psValue)) {
//                procDepth++;
//            }
//        }
//        ArrayList<PSObject> result = new ArrayList<PSObject>();
//        for (int i = 0; i < array.size(); i++) {
//            result.add(array.get(array.size() - i - 1));
//        }
//        return result;
//    }

    private ArrayList<PSObject> gatherArray() {
        if (runtime.getOperandStackSize() < 1) return null;
        ArrayList<PSObject> array = new ArrayList<PSObject>();
        int procDepth = 0;
        do {
            PSObject psObject = runtime.popFromOperandStack();
            if (psObject == null) {
                return null;
            }
            if (PSMark.OPEN_CURLY_BRACE.equals(psObject.getValue()) && procDepth == 0) break;
            array.add(psObject);

            Value psValue = psObject.getValue();
            if (PSMark.OPEN_CURLY_BRACE.equals(psValue)) {
                procDepth--;
            } else if (PSMark.CLOSE_CURLY_BRACE.equals(psValue)) {
                procDepth++;
            }
        } while (true);
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
