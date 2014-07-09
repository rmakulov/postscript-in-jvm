package psObjects.values.simple;

import psObjects.Attribute;
import psObjects.Type;
import runtime.DynamicClassLoader;
import runtime.Runtime;

public abstract class Operator extends SimpleValue {
    protected final Attribute.TreatAs LITERAL = Attribute.TreatAs.LITERAL;
    protected final Attribute.TreatAs EXECUTABLE = Attribute.TreatAs.EXECUTABLE;
    protected runtime.Runtime runtime = Runtime.getInstance();
    protected static Class<?> asm = null;

    public abstract void execute();

    protected Operator() {
        try {
            asm = DynamicClassLoader.instance.loadClass("ASM");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
