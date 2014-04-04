package runtime;

import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class LocalVM {
    private Map<Integer, CompositeValue> map = new HashMap<Integer, CompositeValue>();
    private Set<Integer> initSet = new HashSet<Integer>();
    private Set<Integer> stringSet = new HashSet<Integer>();
    private int maxInt = 0;

    public LocalVM() {
    }

    private LocalVM(Map<Integer, CompositeValue> map, Set<Integer> initSet, Set<Integer> stringSet, int maxInt) {
        this.map = map;
        this.initSet = initSet;
        this.stringSet = stringSet;
        this.maxInt = maxInt;
    }


    public int add(CompositeValue value) {
        int index = maxInt;
        map.put(maxInt, value);
        if (value.determineType() == Type.STRING) {
            stringSet.add(maxInt);
        }
        maxInt++;
        return index;
    }


    public void remove(int index) {
        if (map.remove(index).determineType() == Type.STRING) {
            stringSet.remove(index);
        }
    }


    public void setNewValueAtIndex(int index, CompositeValue value) {
        if (!map.containsKey(index)) {
            try {
                throw new Exception("update object value by wrong index");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put(index, value);
    }

    public boolean contains(Value value) {
        if (!(value instanceof CompositeValue)) return false;
        return map.containsValue(value);
    }

    public CompositeValue get(int index) {
        return map.get(index);
    }

    public String toString() {
        return map.toString();
    }

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
        stringSet.clear();
        initSet.clear();
        maxInt = 0;
    }

    public void initDefaultKeys() {
        initSet.addAll(map.keySet());
    }

    public void clearGarbage(Set<Integer> rootSet) {
        Set<Integer> keys = new HashSet<Integer>(map.keySet());
        for (Integer key : keys) {
            if (!rootSet.contains(key) && !initSet.contains(key)) {
                remove(key);
            }
        }
    }

    public void updateStringValues(LocalVM newLocalVM) {
        for (Integer i : stringSet) {
            CompositeValue value = newLocalVM.get(i);
            if (value.determineType() == Type.STRING) {
                map.put(i, value);
            }
        }
    }

    public LocalVM clone() {
        Map<Integer, CompositeValue> newMap = new HashMap<Integer, CompositeValue>(map);
        Set<Integer> newInitSet = new HashSet<Integer>(initSet);
        Set<Integer> newStringSet = new HashSet<Integer>(stringSet);
        return new LocalVM(newMap, newInitSet, newStringSet, maxInt);
    }
}
