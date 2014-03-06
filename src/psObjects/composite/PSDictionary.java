package psObjects.composite;

import psObjects.reference.Reference;
import psObjects.simple.PSInteger;
import runtime.Runtime;
import runtime.avl.AvlNode;
import runtime.avl.AvlTree;

import java.util.ArrayList;


public class PSDictionary extends CompositeObject {
    private AvlTree tree = new AvlTree();

    public PSDictionary(ArrayList<Reference> list) {
        if (list.size() % 2 == 0) {
            for (int i = 0; i < list.size() - 1; i += 2) {
                //tree = tree.insert(list.get(i), list.get(i + 1));
                tree.setRoot(AvlTree.mutableInsertAVL(tree.getRoot(),new AvlNode(list.get(i), list.get(i + 1))));
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


    public PSDictionary put(Reference key, Reference value) {
        AvlTree newTree = tree.insert(key, value);
        return new PSDictionary(newTree);
    }

    public Reference get(Reference key) {
        return tree.getValue(key);
    }

    public PSDictionary undef(Reference key) {
        AvlTree newTree = tree.remove(key);
        return new PSDictionary(newTree);
    }

    //public PSDictionary copy

    public static PSDictionary initDict(int length) {
        return (PSDictionary) initDictRef(length).getPSObject();
    }

    public static Reference initDictRef(int length) {
        ArrayList<Reference> list = new ArrayList<Reference>();
        for (int i = 0; i < length; i++) {
            Reference key = Runtime.getInstance().createReference(PSString.initString());
            Reference value = Runtime.getInstance().createReference(PSInteger.initInteger());
            list.add(key);
            list.add(value);
        }
        PSDictionary psDictionary = new PSDictionary(list);
        Reference dictRef = Runtime.getInstance().createReference(psDictionary);
        runtime.Runtime.getInstance().pushToDictionaryStack(dictRef);
        return dictRef;
    }

/*    public PSDictionary copy(PSDictionary dictDst) {
        AvlTree newTree = AvlTree.copyTreeToAnother(dictDst.getTree(), tree);
        return new PSDictionary(newTree);
    }*/


    public AvlTree getTree() {
        return tree;
    }

    public PSDictionary copy(PSDictionary srcDict) {
        return new PSDictionary(AvlTree.copyTreeToAnother(tree,srcDict.tree));
    }
}
