package psObjects.values.composite;

import psObjects.Type;
import runtime.LocalVM;
import runtime.graphics.GState;

public class Snapshot extends CompositeValue {
    private LocalVM table;
    private GState gState;

    public Snapshot(LocalVM table) {
        this.table = table;
    }

    public Snapshot(LocalVM table, GState gState) {
        this.table = table;
        this.gState = gState;
    }

    public LocalVM getLocalVM() {
        return table;
    }

    public GState getGState() {
        return gState;
    }

    @Override
    public Type determineType() {
        return Type.SAVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Snapshot)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Snapshot{}";
    }
}
