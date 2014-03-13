package runtime.avl;

import psObjects.reference.Reference;


public class AvlNode implements Comparable<AvlNode> {
    public AvlNode left;
    public AvlNode right;
    public Reference key;
    public Reference value;
    public int balance;

    public AvlNode(Reference key, Reference value) {
        left = right = null;
        balance = 0;
        this.key = key;
        this.value = value;

    }

    public AvlNode(AvlNode node) {
        key = node.key;
        value = node.value;
        balance = node.balance;
    }

    @Override
    public int compareTo(AvlNode node) {
        return key.compareTo(node.key);
    }


   /* public String toString() {
        return "" + runtime.Runtime.getInstance().getPSObjectByReference(key);
    }*/

    public int getCount() {
        return 1 + (left != null ? left.getCount() : 0) + (right != null ? right.getCount() : 0);
    }

    @Override
    public String toString() {
        return "AvlNode{vertices:"+ getCount()+"}"+super.toString();
    }
}
