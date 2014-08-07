package psObjects.values.simple;

import org.objectweb.asm.Opcodes;
import psObjects.Attribute;
import psObjects.Type;
import runtime.Runtime;

public abstract class Operator extends SimpleValue implements Opcodes {
    protected final Attribute.TreatAs LITERAL = Attribute.TreatAs.LITERAL;
    protected final Attribute.TreatAs EXECUTABLE = Attribute.TreatAs.EXECUTABLE;
    protected runtime.Runtime runtime = Runtime.getInstance();

    public abstract void execute();

    public void compile() {
        String clName = this.getClass().getCanonicalName().replace(".", "/");
        runtime.mv.visitFieldInsn(GETSTATIC, clName, "instance", "L" + clName + ";");
        runtime.mv.visitMethodInsn(INVOKEVIRTUAL, clName, "execute", "()V", false);
    }

    ;

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
}
