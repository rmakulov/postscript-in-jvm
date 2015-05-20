package psObjects.values.composite;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.simple.PSName;
import runtime.avl.AvlNode;
import runtime.avl.AvlTree;
import runtime.avl.Pair;

import java.util.ArrayList;
import java.util.Iterator;


public class PSDictionary extends CompositeValue {
    private AvlTree tree = new AvlTree();

    private int version = 0;

    private static int lastVersion = 0;

    public PSDictionary() {
        version = getNextVersion();
    }

    public PSDictionary(int version) {
        this.version = version;
    }

    public PSDictionary(ArrayList<PSObject> list) {
        if (list.size() % 2 == 0) {
            for (int i = 0; i < list.size() - 1; i += 2) {
                //tree = tree.insert(list.get(i), list.get(i + 1));
                tree.setRoot(AvlTree.mutableInsertAVL(tree.getRoot(), new AvlNode(list.get(i), list.get(i + 1))));
            }
        } else {
            // todo: throw exception
        }
    }

    public PSDictionary(AvlTree tree) {
        this.tree = tree;
    }

    public PSDictionary(AvlTree tree, int version) {
        this.version = version;
        this.tree = tree;
    }

    public boolean containsKey(PSObject psKey) {
        return tree.containKey(psKey);
    }

    private PSObject checkHasString(PSObject key) {
        return key.getType() != Type.STRING ? key : key.cvn();
    }

    public PSDictionary put(PSObject key, PSObject value) {
        AvlTree newTree = tree.insert(checkHasString(key), value);
//        System.out.println("DictStackVersion "+key+": "+runtime.getDictStackVersion());
        return new PSDictionary(newTree, getNextVersion());
    }


    public PSObject get(PSObject key) {
        return tree.getValue(checkHasString(key));
    }

    /*remove value from dict*/
    public PSDictionary undef(PSObject key) {
        AvlTree newTree = tree.remove(checkHasString(key));
        return new PSDictionary(newTree, getNextVersion());
    }

    //public PSDictionary copy

    public static PSDictionary initDict(int length) {
        ArrayList<PSObject> list = new ArrayList<PSObject>();
        for (int i = 0; i < length; i++) {
            PSObject key = PSObject.initString();
            PSObject value = PSObject.initInteger();
            list.add(key);
            list.add(value);
        }
        return new PSDictionary(list);
    }

    public AvlTree getTree() {
        return tree;
    }

    public PSDictionary copy(PSDictionary srcDict) {
        return new PSDictionary(AvlTree.copyTreeToAnother(tree, srcDict.tree), getNextVersion());
    }

    public int getVersion() {
        return version;
    }

    @Override
    public Type determineType() {
        return Type.DICTIONARY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSDictionary)) return false;
        return true;
    }

    @Override
    public String toStringView(PSObject object) {
        return "--dict--";
    }

    @Override
    public String toString() {
        Iterator<Pair<PSObject, PSObject>> iterator = tree.iterator();
        StringBuilder sb = new StringBuilder("PSDictionary{ ");
        while (iterator.hasNext()) {
            Pair<PSObject, PSObject> next = iterator.next();
            PSObject key = next.getKey();
            PSObject value = next.getValue();
            Value keyV = next.getKey().getValue();
            Value valueV = next.getValue().getValue();
            if (key.getType() == Type.NAME && ((PSName) key.getValue()).getStrValue().equals("parent")) {
                continue;
            }
            sb.append("(").append(keyV).append(", ").append(valueV).append(") \n");

        }

        return sb.append("}").

                toString();

    }

    public static int getLastVersion() {
        return lastVersion;
    }

    public static int getNextVersion() {
        return lastVersion++;
    }

    public static void clearLastVersion() {
        lastVersion = 0;
    }
}
