package psObjects.values.simple;

import psObjects.Type;

public abstract class Operator extends SimpleValue {

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
