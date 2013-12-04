package psObjects.composite;

import psObjects.PSObject;
import runtime.avl.AvlTree;


public class PSDictionary extends CompositeObject {
    private AvlTree tree = new AvlTree();

    @Override
    public PSObject clone() {
        return this;
    }
}
