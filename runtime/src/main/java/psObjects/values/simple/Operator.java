package psObjects.values.simple;

import psObjects.Attribute;
import psObjects.Type;
import runtime.Runtime;

public abstract class Operator extends SimpleValue {
    protected runtime.Runtime runtime = Runtime.getInstance();
    protected final Attribute.TreatAs LITERAL = Attribute.TreatAs.LITERAL;
    protected final Attribute.TreatAs EXECUTABLE = Attribute.TreatAs.EXECUTABLE;

    public abstract void execute();

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

}
