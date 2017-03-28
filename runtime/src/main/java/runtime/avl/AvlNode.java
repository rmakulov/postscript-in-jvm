package runtime.avl;


import psObjects.PSObject;

public class AvlNode implements Comparable<AvlNode> {
    public AvlNode left;
    public AvlNode right;
    public PSObject key;
    public PSObject value;
    public int balance;

    public AvlNode(PSObject key, PSObject value) {
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


    public int compareTo(AvlNode node) {
        return key.compareTo(node.key);
    }


   /* public String toString() {
        return "" + runtime.Runtime.getInstance().getPSObjectByPSObject(key);
    }*/

    public int getCount() {
        return 1 + (left != null ? left.getCount() : 0) + (right != null ? right.getCount() : 0);
    }

    @Override
    public String toString() {
        return "AvlNode{vertices:"+ getCount()+"}"+super.toString();
    }
}
