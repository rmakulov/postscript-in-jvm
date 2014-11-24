package psObjects.values;


import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.compareableInterfaces.ValueComparable;
import psObjects.values.simple.PSNull;
import runtime.Runtime;

public abstract class Value implements ValueComparable<Value>, Opcodes {
    protected Runtime runtime = Runtime.getInstance();

/*
    public int compareTo(Value object) {
        return this.hashCode()-object.hashCode();
    }*/

    public boolean isNull() {
        return this instanceof PSNull;
    }


    public abstract Value getValue();

    public abstract Type determineType();

    public boolean interpret(PSObject obj) {
        runtime.pushToOperandStack(obj);
        return true;
    }

    public void compile(PSObject obj, int procDepth) {
        try {
            throw new Exception("try to call common compile method instead of specific one");
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        //runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
//        String name = runtime.bcGenManager.bytecodeName;
//        runtime.bcGenManager.mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
//        runtime.bcGenManager.mv.visitFieldInsn(GETFIELD, "psObjects/values/Value", "runtime", "Lruntime/Runtime;");
//        runtime.bcGenManager.mv.visitFieldInsn(GETFIELD, "runtime/Runtime", "bcGenManager", "Lruntime/BytecodeGeneratorManager;");
//        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/BytecodeGeneratorManager", "getCur", "()LpsObjects/PSObject;", false);
//        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "getValue", "()LpsObjects/values/Value;", false);
//        runtime.bcGenManager.mv.visitTypeInsn(CHECKCAST, "psObjects/values/simple/PSBytecode");
//        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/values/simple/PSBytecode", "getArg", "()LpsObjects/PSObject;", false);
//        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/values/Value", "interpret", "(LpsObjects/PSObject;)V", false);
    }

    protected void checkExitCompile() {
        MethodVisitor mv = runtime.bcGenManager.mv;
        Label l7 = new Label();
        mv.visitJumpInsn(IFNE, l7);
        mv.visitInsn(ICONST_0);
        mv.visitInsn(IRETURN);
        mv.visitLabel(l7);
    }

    public Integer compareTo(Value o) {
        return o.compareGrade() == -1 || compareGrade() == -1 ? null : compareGrade() - o.compareGrade();
    }

    //need to override in comparable sons
    public int compareGrade() {
        return -1;
    }

    public abstract boolean equals(Object o);

    public abstract String toStringView(PSObject obj);

    public void deepCompile(PSObject psObject) {
        compile(psObject, 0); //different for PSName
    }
}
