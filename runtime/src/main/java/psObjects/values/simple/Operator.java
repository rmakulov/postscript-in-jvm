package psObjects.values.simple;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;

public abstract class Operator extends SimpleValue implements Opcodes {
    protected final Attribute.TreatAs LITERAL = Attribute.TreatAs.LITERAL;
    protected final Attribute.TreatAs EXECUTABLE = Attribute.TreatAs.EXECUTABLE;

    @Override
    public boolean interpret(PSObject obj) {
        interpret();
        return true;
    }

    public abstract void interpret();

    @Override
    public void compile(PSObject obj) {
        //todo make in runtime current bytecode, I think it is ready
        MethodVisitor mv = runtime.bcGenManager.mv;
        String clName = this.getClass().getCanonicalName().replace(".", "/");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitFieldInsn(GETSTATIC, clName, "instance", "L" + clName + ";");
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
        checkExitCompile();
        //runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, clName, "interpret", "()V", false);
    }


    protected Operator() {
    }

    @Override
    public Type determineType() {
        return Type.OPERATOR;
    }

    public abstract PSName getDefaultKeyName();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operator)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "name=" + getDefaultKeyName().getStrValue() +
                "}";
    }

    protected void fail() {
        try {
            throw new Exception(this + " failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toStringView(PSObject object) {
        return toString();
    }
}
