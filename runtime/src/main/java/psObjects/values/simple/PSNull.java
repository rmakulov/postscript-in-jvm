package psObjects.values.simple;

import psObjects.Type;

public class PSNull extends SimpleValue {
    int value;

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

}
