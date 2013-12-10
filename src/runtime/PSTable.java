package runtime;

import psObjects.PSObject;

import java.util.ArrayList;
import java.util.Iterator;


public class PSTable implements Cloneable {
    private ArrayList<PSObject> table = new ArrayList<PSObject>();

    public PSTable() {
    }

    public PSTable(ArrayList<PSObject> table) {
        this.table = table;
    }

    public PSTable add(PSObject psObject) {
        Iterator<PSObject> iterator = table.iterator();
        ArrayList<PSObject> newTable = new ArrayList<PSObject>();
        while (iterator.hasNext()) {
            newTable.add(iterator.next());
        }
        newTable.add(psObject);
        return new PSTable(newTable);
    }

    public PSTable remove(int index) {
        Iterator<PSObject> iterator = table.iterator();
        ArrayList<PSObject> newTable = new ArrayList<PSObject>();
        while (iterator.hasNext()) {
            newTable.add(iterator.next());
        }
        newTable.remove(index);
        return new PSTable(newTable);
    }

    public int getIndex(PSObject psObject) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).equals(psObject)) return i;
        }
        return -1;
    }

    public boolean contains(PSObject psObject) {
        return table.contains(psObject);
    }

    public PSObject get(int index) {
        return table.get(index);
    }

    public String toString() {
        return table.toString();
    }

    public int size() {
        return table.size();
    }
}
