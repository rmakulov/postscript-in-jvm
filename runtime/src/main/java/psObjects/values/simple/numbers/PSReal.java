package psObjects.values.simple.numbers;

import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import psObjects.Type;
import runtime.Context;
import runtime.Runtime;

public class PSReal extends PSNumber {
    private double value;

    public PSReal(double value) {
        this.value = value;
    }

    @Override
    public double getRealValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PSReal{" +
                "value=" + value +
                "}";
    }

    @Override
    public Type determineType() {
        return Type.REAL;
    }

    @Override
    public void compile(Context context, PSObject obj) {
        String name = context.bcGenManager.bytecodeName;
        MethodVisitor mv = context.bcGenManager.mv;

        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/numbers/PSReal");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(value);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/numbers/PSReal", "<init>", "(D)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);

        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(Lruntime/Context;I)Z", false);
        checkExitCompile(context);
    }

    @Override
    public String toStringView(PSObject object) {
        return value + "";
    }

    public static void compile(Context context, double value) {
        Runtime runtime = Runtime.getInstance();
//        runtime.pushToOperandStack(new PSObject(new PSReal(value)));
        //runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        String name = context.bcGenManager.bytecodeName;
        MethodVisitor mv = context.bcGenManager.mv;
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");

        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/numbers/PSReal");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(value);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/numbers/PSReal", "<init>", "(D)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);

        mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Context", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
    }
}
