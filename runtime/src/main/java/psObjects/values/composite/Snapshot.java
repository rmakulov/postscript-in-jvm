package psObjects.values.composite;

import psObjects.Type;
import runtime.LocalVM;

public class Snapshot extends CompositeValue {
    private LocalVM table = new LocalVM();

    public Snapshot(LocalVM table) {
        this.table = table;
    }

    public LocalVM getLocalVM() {
        return table;
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
