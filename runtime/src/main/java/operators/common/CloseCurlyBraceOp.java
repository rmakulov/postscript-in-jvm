package operators.common;

import org.objectweb.asm.MethodVisitor;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.Value;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBytecode;
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

        if (runtime.isCompiling && !runtime.bcGenManager.isSleep()) {
            runtime.bcGenManager.endBytecode();
            PSBytecode originBytecode = runtime.bcGenManager.getCur();
//            BytecodeProc bytecodeProc = new BytecodeProc(originBytecode);

//            runtime.pushToOperandStack(new PSObject(bytecodeProc));
            runtime.pushToOperandStack(new PSObject(originBytecode));
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
        //todo alright
//        (runtime.bcGenManager.getCur()).compile(null);

//        runtime.pushToOperandStack(new PSObject(runtime.bcGenManager.getCur()));
        runtime.bcGenManager.endBytecode();
        PSBytecode bytecode = runtime.bcGenManager.getCur();
        String bytecodeName = bytecode.getStrValue();

        MethodVisitor mv = runtime.bcGenManager.mv;
        String name = runtime.bcGenManager.bytecodeName;
        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/PSBytecode");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(bytecodeName);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSBytecode", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);

    }


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
