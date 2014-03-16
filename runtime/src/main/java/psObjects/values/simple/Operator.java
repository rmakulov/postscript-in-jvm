package psObjects.values.simple;

import psObjects.Type;
import runtime.*;
import runtime.Runtime;

public abstract class Operator extends SimpleValue {

    protected runtime.Runtime runtime = Runtime.getInstance();

    public abstract void execute();

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

}
