package psObjects.values.composite;

import psObjects.PSObject;
import psObjects.Type;
import runtime.LocalVM;
import runtime.graphics.GState;

import java.util.HashMap;

public class Snapshot extends CompositeValue {
    private LocalVM table;
    private GState gState;
    private HashMap<String, Integer> nameVersions;

    public Snapshot(LocalVM table) {
        this.table = table;
    }

    public Snapshot(LocalVM table, GState gState, HashMap<String, Integer> nameVersions) {
        this.table = table;
        this.gState = gState;
        this.nameVersions = new HashMap<String, Integer>(nameVersions);
    }

    public HashMap<String, Integer> getNameVersions() {
        return nameVersions;
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
    public String toStringView(PSObject object) {
        return "--snapshot--";
    }

    @Override
    public String toString() {
        return "Snapshot{}";
    }
}
