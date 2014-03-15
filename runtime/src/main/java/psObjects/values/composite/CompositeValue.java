package psObjects.values.composite;

import psObjects.values.Value;


public abstract class CompositeValue extends Value {
    @Override
    public Value getValue() {
        return this;
    }
}
