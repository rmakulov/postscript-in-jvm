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
import runtime.Context;
import runtime.Runtime;

import java.util.ArrayList;

public class CloseCurlyBraceOp extends Operator {
    public static final CloseCurlyBraceOp instance = new CloseCurlyBraceOp();

    protected CloseCurlyBraceOp() {
        super();
    }

    @Override
    public void interpret(Context context) {

        if (runtime.isCompiling && !context.bcGenManager.isSleep()) {
            context.bcGenManager.endBytecode();
            PSBytecode originBytecode = context.bcGenManager.getCur();
//            BytecodeProc bytecodeProc = new BytecodeProc(originBytecode);

//            context.pushToOperandStack(new PSObject(bytecodeProc));
            context.pushToOperandStack(new PSObject(originBytecode));
        } else {
            //context.pushToOperandStack(new PSObject(PSMark.CLOSE_CURLY_BRACE));
            ArrayList<PSObject> gatherArray = gatherArray(context);
            if (gatherArray == null) {
                return;
            }
            PSArray result = new PSArray(gatherArray);
            context.pushToOperandStack(new PSObject(result, Attribute.TreatAs.EXECUTABLE));
        }
    }


    public static void compile(Context context) {
        runtime.Runtime runtime = Runtime.getInstance();
//        context.pushToOperandStack(new PSObject(runtime.bcGenManager.getCur()));
        context.bcGenManager.endBytecode();
        PSBytecode bytecode = context.bcGenManager.getCur();
        String bytecodeName = bytecode.getStrValue();

        MethodVisitor mv = context.bcGenManager.mv;
        String name = context.bcGenManager.bytecodeName;
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/PSBytecode");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(bytecodeName);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSBytecode", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Context", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);

    }


    private ArrayList<PSObject> gatherArray(Context context) {
        if (context.getOperandStackSize() < 1) return null;
        ArrayList<PSObject> array = new ArrayList<PSObject>();
        int procDepth = 0;
        do {
            PSObject psObject = context.popFromOperandStack();
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
