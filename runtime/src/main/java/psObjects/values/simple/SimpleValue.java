package psObjects.values.simple;

import psObjects.values.Value;


public abstract class SimpleValue extends Value {
    @Override
    public Value getValue() {
        return this;
    }
}
