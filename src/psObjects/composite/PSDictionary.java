package psObjects.composite;

import psObjects.PSObject;
import psObjects.simple.PSInteger;
import runtime.avl.AvlTree;

import java.util.ArrayList;


public class PSDictionary extends CompositeObject {
    private AvlTree tree = new AvlTree();

    public PSDictionary(ArrayList<PSObject> list) {
        if (list.size() % 2 == 0) {
            for (int i = 0; i < list.size() - 1; i += 2) {
                tree = tree.insert(list.get(i), list.get(i + 1));
            }
        } else {
            // todo: throw exception
        }
    }

    public PSDictionary() {
    }

    public PSDictionary(AvlTree tree) {
        this.tree = tree;
    }


    public PSDictionary put(PSObject key, PSObject value) {
        AvlTree newTree = tree.insert(key, value);
        return new PSDictionary(newTree);
    }

    public PSObject get(PSObject key) {
        return tree.getValue(key);
    }

    public PSDictionary undef(PSObject key) {
        AvlTree newTree = tree.remove(key);
        return new PSDictionary(newTree);
    }

    public static PSDictionary initDict(int length) {
        ArrayList<PSObject> list =new ArrayList<PSObject>();
        for (int i = 0; i < length; i++) {
            PSObject key = PSString.initString();
            PSObject value = PSInteger.initInteger();
            list.add(key);
            list.add(value);
        }
        return new PSDictionary(list);
    }
}
