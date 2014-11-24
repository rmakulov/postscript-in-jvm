package psObjects.values.simple;

import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import psObjects.Type;

public class PSNull extends SimpleValue {
    public final static PSNull NULL = new PSNull();


    private PSNull() {
    }

    @Override
    public Type determineType() {
        return Type.NULL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSNull)) return false;
        return true;
    }

    @Override
    public void compile(PSObject obj, int procDepth) {
//        runtime.pushToOperandStack(new PSObject(PSNull.NULL));
        String name = runtime.bcGenManager.bytecodeName;
        MethodVisitor mv = runtime.bcGenManager.mv;
//        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitFieldInsn(GETSTATIC, "psObjects/values/simple/PSNull", "NULL", "LpsObjects/values/simple/PSNull;");
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
        mv.visitLdcInsn(procDepth);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
        checkExitCompile();
    }

    @Override
    public String toStringView(PSObject obj) {
        return "null";
    }

    @Override
    public String toString() {
        return "PSNull";
    }
}
