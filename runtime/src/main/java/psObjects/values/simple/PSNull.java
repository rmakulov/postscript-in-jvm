package psObjects.values.simple;

import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import psObjects.Type;
import runtime.Context;

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
    public void compile(Context context, PSObject obj) {
//        runtime.pushToOperandStack(new PSObject(PSNull.NULL));
        String name = context.bcGenManager.bytecodeName;
        MethodVisitor mv = context.bcGenManager.mv;
//        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitFieldInsn(GETSTATIC, "psObjects/values/simple/PSNull", "NULL", "LpsObjects/values/simple/PSNull;");
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(Lruntime/Context;I)Z", false);
        checkExitCompile(context);
    }

    @Override
    public String toStringView(PSObject object) {
        return "null";
    }

    @Override
    public String toString() {
        return "PSNull";
    }
}
