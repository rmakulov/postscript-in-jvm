package runtime.avl;

import psObjects.PSObject;

import java.util.ArrayList;

import static psObjects.PSNull.NULL;

/**
 * This class is the complete and tested implementation of an AVL-tree.
 */
public class AvlTree {

    protected AvlNode root; // the root node
    private int count = 0;

    public AvlTree(AvlNode root) {
        if (root == null) return;
        this.root = root;
        count = 1;
    }

    public AvlTree() {

    }

    private void incCount() {
        count++;
    }

    private void decCount() {
        count--;
    }

    public AvlNode getRoot() {
        return root;
    }
    /***************************** Core Functions ************************************/

    /**
     * Add a new element with key "k" into the tree.
     *
     * @param key   The key of the new node.
     * @param value The value of the new node.
     */
    public AvlTree insert(PSObject key, PSObject value) {
        if (root == null) {
            return new AvlTree(new AvlNode(key, value));
        }
        // create new node
        AvlNode newRoot = new AvlNode(root);
        copyNode(newRoot, root);
        AvlNode node = new AvlNode(key, value);
        // start recursive procedure for inserting the node
        insertAVL(newRoot, node);
        return new AvlTree(newRoot);
    }

    private void copyNode(AvlNode newNode, AvlNode node) {

        AvlNode left = node.left;
        AvlNode right = node.right;

        if (left != null) {
            newNode.left = new AvlNode(left);
            copyNode(newNode.left, left);
            incCount();
        }
        if (right != null) {
            newNode.right = new AvlNode(right);
            copyNode(newNode.right, right);
            incCount();
        }
    }

    public static AvlTree copyTreeToAnother(AvlTree treeDst, AvlTree treeSrc) {
        AvlTree treeRes = new AvlTree();
        copyTreeToAnotherAVL(treeRes, treeDst.getRoot());
        copyTreeToAnotherAVL(treeRes, treeSrc.getRoot());
        return treeRes;
    }

    public static void copyTreeToAnotherAVL(AvlTree newTree, AvlNode node) {
        if (node == null) return;
        AvlNode newNode = new AvlNode(node);
        AvlNode newRoot = newTree.getRoot();
        if (newRoot == null) {
            newTree.setRoot(newNode);
        } else {
            newTree.insertAVL(newRoot, newNode);
        }

        AvlNode left = node.left;
        AvlNode right = node.right;

        if (left != null) {
            copyTreeToAnotherAVL(newTree, left);
        }
        if (right != null) {
            copyTreeToAnotherAVL(newTree, right);
        }

    }

    /**
     * Recursive method to insert a node into a tree.
     *
     * @param p The node currently compared, usually you start with the root.
     * @param q The node to be inserted.
     */
    public void insertAVL(AvlNode p, AvlNode q) {
        // If  node to compare is null, the node is inserted. If the root is null, it is the root of the tree.
        if (p == null) {
            this.root = q;
        } else {

            // If compare node is smaller, continue with the left node
            if (q.key.compareTo(p.key) < 0) {
                if (p.left == null) {
                    p.left = q;
                    q.parent = p;
                    incCount();
                    // Node is inserted now, continue checking the balance
                    recursiveBalance(p);
                } else {
                    insertAVL(p.left, q);
                }

            } else if (q.key.compareTo(p.key) > 0) {
                if (p.right == null) {
                    p.right = q;
                    q.parent = p;
                    incCount();

                    // Node is inserted now, continue checking the balance
                    recursiveBalance(p);
                } else {
                    insertAVL(p.right, q);
                }
            } else {
                // This node already exists
                p.value = q.value;
            }
        }
    }

    /**
     * Check the balance for each node recursivly and call required methods for balancing the tree until the root is reached.
     *
     * @param cur : The node to check the balance for, usually you start with the parent of a leaf.
     */
    public void recursiveBalance(AvlNode cur) {

        // we do not use the balance in this class, but the store it anyway
        setBalance(cur);
        int balance = cur.balance;

        // check the balance
        if (balance == -2) {

            if (height(cur.left.left) >= height(cur.left.right)) {
                cur = rotateRight(cur);
            } else {
                cur = doubleRotateLeftRight(cur);
            }
        } else if (balance == 2) {
            if (height(cur.right.right) >= height(cur.right.left)) {
                cur = rotateLeft(cur);
            } else {
                cur = doubleRotateRightLeft(cur);
            }
        }

        // we did not reach the root yet
        if (cur.parent != null) {
            recursiveBalance(cur.parent);
        } else {
            this.root = cur;
            System.out.println("------------ Balancing finished ----------------");
        }
    }

    /**
     * Removes a node from the tree, if it is existent.
     */
    public AvlTree remove(PSObject key) {
        // First we must find the node, after this we can delete it.
        AvlNode newRoot = new AvlNode(root);
        copyNode(newRoot, root);
        // start recursive procedure for inserting the node
        removeAVL(newRoot, key);
        return new AvlTree(newRoot);
    }

    /**
     * Finds a node and calls a method to remove the node.
     *
     * @param node The node to start the search.
     * @param key  The KEY of node to undef.
     */
    public void removeAVL(AvlNode node, PSObject key) {
        if (node == null) {
            // der Wert existiert nicht in diesem Baum, daher ist nichts zu tun
            return;
        } else {
            if (node.key.compareTo(key) > 0) {
                removeAVL(node.left, key);
            } else if (node.key.compareTo(key) < 0) {
                removeAVL(node.right, key);
            } else if (node.key.compareTo(key) == 0) {
                // we found the node in the tree.. now lets go on!
                removeFoundNode(node);
            }
        }
    }

    public PSObject getValue(PSObject key) {
        return getValueAVL(this.root, key);
    }

    /**
     * Finds a node and calls a method to get value of the node.
     *
     * @param node The node to start the search.
     * @param key  The KEY of node to find and get value.
     */
    public PSObject getValueAVL(AvlNode node, PSObject key) {
        if (node != null) {
            if (node.key.compareTo(key) > 0) {
                return getValueAVL(node.left, key);
            } else if (node.key.compareTo(key) < 0) {
                return getValueAVL(node.right, key);
            } else if (node.key.compareTo(key) == 0) {
                // we found the node in the tree.. now lets go on!
                return node.value;
            }
        }
        //TODO: think about return exception
        return NULL;
    }

    /**
     * Removes a node from a AVL-Tree, while balancing will be done if necessary.
     *
     * @param q The node to be removed.
     */
    public void removeFoundNode(AvlNode q) {
        decCount();
        AvlNode r;
        // at least one child of q, q will be removed directly
        if (q.left == null || q.right == null) {
            // the root is deleted
            if (q.parent == null) {
                this.root = null;
                q = null;
                return;
            }
            r = q;
        } else {
            // q has two children --> will be replaced by successor
            r = successor(q);
            q.key = r.key;
        }

        AvlNode p;
        if (r.left != null) {
            p = r.left;
        } else {
            p = r.right;
        }

        if (p != null) {
            p.parent = r.parent;
        }

        if (r.parent == null) {
            this.root = p;
        } else {
            if (r == r.parent.left) {
                r.parent.left = p;
            } else {
                r.parent.right = p;
            }
            // balancing must be done until the root is reached.
            recursiveBalance(r.parent);
        }
        r = null;
    }

    /**
     * Left rotation using the given node.
     *
     * @param n The node for the rotation.
     * @return The root of the rotated tree.
     */
    public AvlNode rotateLeft(AvlNode n) {

        AvlNode v = n.right;
        v.parent = n.parent;

        n.right = v.left;

        if (n.right != null) {
            n.right.parent = n;
        }

        v.left = n;
        n.parent = v;

        if (v.parent != null) {
            if (v.parent.right == n) {
                v.parent.right = v;
            } else if (v.parent.left == n) {
                v.parent.left = v;
            }
        }

        setBalance(n);
        setBalance(v);

        return v;
    }

    /**
     * Right rotation using the given node.
     *
     * @param n The node for the rotation
     * @return The root of the new rotated tree.
     */
    public AvlNode rotateRight(AvlNode n) {

        AvlNode v = n.left;
        v.parent = n.parent;

        n.left = v.right;

        if (n.left != null) {
            n.left.parent = n;
        }

        v.right = n;
        n.parent = v;


        if (v.parent != null) {
            if (v.parent.right == n) {
                v.parent.right = v;
            } else if (v.parent.left == n) {
                v.parent.left = v;
            }
        }

        setBalance(n);
        setBalance(v);

        return v;
    }

    /**
     * @param u The node for the rotation.
     * @return The root after the double rotation.
     */
    public AvlNode doubleRotateLeftRight(AvlNode u) {
        u.left = rotateLeft(u.left);
        return rotateRight(u);
    }

    /**
     * @param u The node for the rotation.
     * @return The root after the double rotation.
     */
    public AvlNode doubleRotateRightLeft(AvlNode u) {
        u.right = rotateRight(u.right);
        return rotateLeft(u);
    }

/***************************** Helper Functions ************************************/

    /**
     * Returns the successor of a given node in the tree (search recursivly).
     *
     * @param q The predecessor.
     * @return The successor of node q.
     */
    public AvlNode successor(AvlNode q) {
        if (q.right != null) {
            AvlNode r = q.right;
            while (r.left != null) {
                r = r.left;
            }
            return r;
        } else {
            AvlNode p = q.parent;
            while (p != null && q == p.right) {
                q = p;
                p = q.parent;
            }
            return p;
        }
    }

    /**
     * Calculating the "height" of a node.
     *
     * @param cur
     * @return The height of a node (-1, if node is not existent eg. NULL).
     */
    private int height(AvlNode cur) {
        if (cur == null) {
            return -1;
        }
        if (cur.left == null && cur.right == null) {
            return 0;
        } else if (cur.left == null) {
            return 1 + height(cur.right);
        } else if (cur.right == null) {
            return 1 + height(cur.left);
        } else {
            return 1 + maximum(height(cur.left), height(cur.right));
        }
    }

    /**
     * Return the maximum of two integers.
     */
    private int maximum(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * Only for debugging purposes. Gives all information about a node.
     *
     * @param node The node to write information about.
     */
    public void debug(AvlNode node) {
        PSObject l = NULL;
        PSObject r = NULL;
        PSObject p = NULL;
        if (node.left != null) {
            l = node.left.key;
        }
        if (node.right != null) {
            r = node.right.key;
        }
        if (node.parent != null) {
            p = node.parent.key;
        }

        System.out.println("Left: " + l + " Key: " + node + " Right: " + r + " Parent: " + p + " Balance: " + node.balance);

        if (node.left != null) {
            debug(node.left);
        }
        if (node.right != null) {
            debug(node.right);
        }
    }

    private void setBalance(AvlNode cur) {
        cur.balance = height(cur.right) - height(cur.left);
    }

    /**
     * Calculates the Inorder traversal of this tree.
     *
     * @return A Array-List of the tree in inorder traversal.
     */
    final protected ArrayList<AvlNode> inorder() {
        ArrayList<AvlNode> ret = new ArrayList<AvlNode>();
        inorder(root, ret);
        return ret;
    }

    /**
     * Function to calculate inorder recursivly.
     *
     * @param n  The current node.
     * @param io The list to save the inorder traversal.
     */
    final protected void inorder(AvlNode n, ArrayList<AvlNode> io) {
        if (n == null) {
            return;
        }
        inorder(n.left, io);
        io.add(n);
        inorder(n.right, io);
    }

    public void setRoot(AvlNode root) {
        this.root = root;
        root.parent = null;
        incCount();
    }
}


