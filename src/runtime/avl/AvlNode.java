package runtime.avl;

import psObjects.PSObject;


public class AvlNode {
    public AvlNode left;
    public AvlNode right;
    public AvlNode parent;
    public PSObject key;
    public PSObject value;
    public int balance;

    public AvlNode(PSObject key, PSObject value) {
        left = right = parent = null;
        balance = 0;
        this.key = key;
        this.value = value;

    }

    public AvlNode(AvlNode node) {
        left = node.left;
        right = node.right;
        parent =node.parent;
        key = node.key;
        value = node.value;
        balance = node.balance;
    }

    public String toString() {
        return "" + key;
    }

}
