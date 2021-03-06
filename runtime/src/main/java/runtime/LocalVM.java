package runtime;

import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class LocalVM {
    private Map<Integer, CompositeValue> map = new ConcurrentHashMap<Integer, CompositeValue>();
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
        CompositeValue compositeValue = map.get(index);
        if (compositeValue==null){
            try {
                throw new Exception("Composite value cannot be find by ref in localVM");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return compositeValue;
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
       // System.out.println("Before clean: "+map.size());
        Set<Integer> keys = new HashSet<Integer>(map.keySet());
        for (Integer key : keys) {
            if (!rootSet.contains(key) && !initSet.contains(key) &&!(stringSet.contains(key))) {
                remove(key);
            }
        }
        //System.out.println("After clean: "+map.size());
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
        for (Integer i : map.keySet()) {
            CompositeValue value = newMap.get(i);
            if (value instanceof PSArray) {
                newMap.put(i, ((PSArray) value).clone());
            }
        }
        Set<Integer> newInitSet = new HashSet<Integer>(initSet);
        Set<Integer> newStringSet = new HashSet<Integer>(stringSet);
        return new LocalVM(newMap, newInitSet, newStringSet, maxInt);
    }
}
