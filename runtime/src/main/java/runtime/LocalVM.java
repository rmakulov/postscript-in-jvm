package runtime;

import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;

import java.util.ArrayList;
import java.util.Iterator;


public class LocalVM {
    private ArrayList<CompositeValue> table = new ArrayList<CompositeValue>();

    public LocalVM() {
    }

    public LocalVM(ArrayList<CompositeValue> table) {
        this.table = table;
    }

    public LocalVM add(CompositeValue value) {
        Iterator<CompositeValue> iterator = table.iterator();
        ArrayList<CompositeValue> newTable = new ArrayList<CompositeValue>();
        while (iterator.hasNext()) {
            newTable.add(iterator.next());
        }
        newTable.add(value);
        return new LocalVM(newTable);
    }

    public LocalVM remove(int index) {
        Iterator<CompositeValue> iterator = table.iterator();
        ArrayList<CompositeValue> newTable = new ArrayList<CompositeValue>();
        while (iterator.hasNext()) {
            newTable.add(iterator.next());
        }
        newTable.remove(index);
        return new LocalVM(newTable);
    }

    public int getIndex(CompositeValue value) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).equals(value)) return i;
        }
        return -1;
    }

    public boolean contains(Value value) {
        if(!(value instanceof CompositeValue)) return false;
        return table.contains(value);
    }

    public CompositeValue get(int index) {
        return table.get(index);
    }

    public String toString() {
        return table.toString();
    }

    public int size() {
        return table.size();
    }

    public void clear() {
        table.clear();
    }
}
