package runtime.avl;

//import References.Reference;

import psObjects.PSObject;
import psObjects.values.simple.PSNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * This class is the complete and tested implementation of an AVL-tree.
 */
public class AvlTree implements Iterable<Pair<PSObject, PSObject>> {

    protected AvlNode root; // the root node

    public AvlTree(AvlNode root) {
        if (root == null) return;
        this.root = root;
    }

    public AvlTree() {

    }

    public Iterator<Pair<PSObject, PSObject>> iterator() {
        return new DepthFirstIterator();
    }


    public AvlNode getRoot() {
        return root;
    }

    /**
     * ************************** Core Functions ***********************************
     */


    //full copy with children
    private AvlNode linkCopyNode(AvlNode node) {
        AvlNode newNode = new AvlNode(node);

        AvlNode left = node.left;
        AvlNode right = node.right;

        if (left != null) {
            newNode.left = linkCopyNode(left);
        }
        if (right != null) {
            newNode.right = linkCopyNode(right);
        }
        return newNode;
    }

    //copies only key,value and children's links
    private AvlNode valueCopyNode(AvlNode node) {
        AvlNode newNode = new AvlNode(node);

        AvlNode left = node.left;
        AvlNode right = node.right;

        if (left != null) {
            newNode.left = left;
        }
        if (right != null) {
            newNode.right = right;
        }
        return newNode;
    }

    public static AvlTree copyTreeToAnother(AvlTree treeDst, AvlTree treeSrc) {
        AvlTree treeRes = new AvlTree();
        copyTreeToAnotherAVL(treeRes, treeDst.getRoot());
        copyTreeToAnotherAVL(treeRes, treeSrc.getRoot());
        return treeRes;
    }

    private static void copyTreeToAnotherAVL(AvlTree newTree, AvlNode node) {
        if (node == null) return;
        AvlNode newNode = new AvlNode(node);
        AvlNode newRoot = newTree.getRoot();
        if (newRoot == null) {
            newTree.setRoot(newNode);
        } else {
            newTree.setRoot(mutableInsertAVL(newRoot, node));
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
     * Add a new element with key "k" into the tree.
     *
     * @param key   The key of the new node.
     * @param value The value of the new node.
     */
    public AvlTree insert(PSObject key, PSObject value) {
        if (root == null) {
            return new AvlTree(new AvlNode(key, value));
        }
        if (key.getValue() instanceof PSNull) {
            try {
                throw new Exception("Putting Null as key into avl tree");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // create new node
        AvlNode newRoot = valueCopyNode(root);
        AvlNode node = new AvlNode(key, value);
        // start recursive procedure for inserting the node
        return new AvlTree(insertAVL(newRoot, node));
    }

    public static AvlNode mutableInsertAVL(AvlNode p, AvlNode node) {
        if (p == null) return new AvlNode(node);
        // If compare node is smaller, continue with the left node
        if (node.compareTo(p) < 0) {
            if (p.left == null) {
                p.left = new AvlNode(node);
                // Node is inserted now, continue checking the balance
            } else {
                p.left = mutableInsertAVL(p.left, node);
            }

        } else if (node.compareTo(p) > 0) {
            if (p.right == null) {
                p.right = new AvlNode(node);
                // Node is inserted now, continue checking the balance
            } else {
                p.right = mutableInsertAVL(p.right, node);
            }
        } else {
            // This node already exists
            p.value = node.value;
        }

        return balance(p);
    }

    /**
     * Recursive method to insert a node into a tree.
     *
     * @param p       The node currently compared, usually you start with the root.
     * @param newNode The node to be inserted.
     */
    private AvlNode insertAVL(AvlNode p, AvlNode newNode) {
        // If  node to compare is null, the node is inserted. If the root is null, it is the root of the tree.
        if (p == null) {
            this.root = newNode;
        } else {

            // If compare node is smaller, continue with the left node
            if (newNode.compareTo(p) < 0) {
                if (p.left == null) {
                    p.left = newNode;
                    // Node is inserted now, continue checking the balance
                } else {
                    p.left = valueCopyNode(p.left);
                    p.left = insertAVL(p.left, newNode);
                }

            } else if (newNode.compareTo(p) > 0) {
                if (p.right == null) {
                    p.right = newNode;
                    // Node is inserted now, continue checking the balance
                } else {
                    p.right = valueCopyNode(p.right);
                    p.right = insertAVL(p.right, newNode);
                }
            } else {
                // This node already exists
                p.value = newNode.value;
            }
        }
        return balance(p);
    }

    /**
     * Check the balance for each node recursivly and call required methods for balancing the tree until the root is reached.
     *
     * @param cur : The node to check the balance for, usually you start with the parent of a leaf.
     */
    private static AvlNode balance(AvlNode cur) {
        // we do not use the balance in this class, but the store it anyway
        int balance = setBalance(cur);

        // check the balance
        if (balance == -2) {

            if (setBalance(cur.left) <= 0) {
                cur = rotateRight(cur);
            } else {
                cur = doubleRotateLeftRight(cur);
            }
        } else if (balance == 2) {
            if (setBalance(cur.right) >= 0) {
                cur = rotateLeft(cur);
            } else {
                cur = doubleRotateRightLeft(cur);
            }
        }
        return cur;
    }

    /**
     * Removes a node from the tree, if it is existent.
     */
    public AvlTree remove(PSObject key) {
        // First we must find the node, after this we can delete it.
        AvlNode newRoot = valueCopyNode(root);
        // start recursive procedure for inserting the node
        return new AvlTree(removeAVL(newRoot, key));
    }

    /**
     * Finds a node and calls a method to remove the node.
     *
     * @param node The node to start the search.
     * @param key  The KEY of node to undef.
     */
    private AvlNode removeAVL(AvlNode node, PSObject key) {
        if (node == null) {
            // the value does not exist in this tree, so nothing needs to be done
            return null;
        } else {
            if (node.key.compareTo(key) > 0) {
                node.left = removeAVL(node.left, key);
            } else if (node.key.compareTo(key) < 0) {
                node.right = removeAVL(node.right, key);
            } else if (node.key.compareTo(key) == 0) {
                // we found the node in the tree.. now lets go on!
                AvlNode newNode = linkCopyNode(node);
                return removeFoundNode(newNode);
            }
        }
        return balance(node);
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
    private PSObject getValueAVL(AvlNode node, PSObject key) {
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
        return null;
    }

    public boolean containKey(PSObject key) {
        return containKeyAVL(this.root, key);
    }

    private boolean containKeyAVL(AvlNode root, PSObject key) {
        if (root != null) {
            if (root.key.compareTo(key) > 0) {
                return containKeyAVL(root.left, key);
            } else if (root.key.compareTo(key) < 0) {
                return containKeyAVL(root.right, key);
            } else if (root.key.compareTo(key) == 0) {
                // we found the node in the tree.. now lets go on!
                return true;
            }
        }
        //TODO: think about return exception
        return false;
    }


    /**
     * Removes a node from a AVL-Tree, while balancing will be done if necessary.
     *
     * @param p The node to be removed.
     */

    private AvlNode removeFoundNode(AvlNode p) {
        AvlNode q = p.left;
        AvlNode r = p.right;
        if (r == null) return q;
        AvlNode min = findMin(r);
        min.right = removeMin(r);
        min.left = q;
        return balance(min);

    }

    private AvlNode removeMin(AvlNode p) {
        if (p.left == null)
            return p.right;
        p.left = removeMin(p.left);
        return balance(p);
    }

    private AvlNode findMin(AvlNode p) {
        return p.left != null ? findMin(p.left) : p;
    }

    /**
     * Left rotation using the given node.
     *
     * @param q The node for the rotation.
     * @return The root of the rotated tree.
     */
    private static AvlNode rotateLeft(AvlNode q) {
        AvlNode p = q.right;
        q.right = p.left;
        p.left = q;
        setBalance(q);
        setBalance(p);
        return p;
    }

    /**
     * Right rotation using the given node.
     *
     * @param p The node for the rotation
     * @return The root of the new rotated tree.
     */

    private static AvlNode rotateRight(AvlNode p) {
        AvlNode q = p.left;
        p.left = q.right;
        q.right = p;
        setBalance(p);
        setBalance(q);
        return q;
    }

    /**
     * @param u The node for the rotation.
     * @return The root after the double rotation.
     */
    private static AvlNode doubleRotateLeftRight(AvlNode u) {
        u.left = rotateLeft(u.left);
        return rotateRight(u);
    }

    /**
     * @param u The node for the rotation.
     * @return The root after the double rotation.
     */
    private static AvlNode doubleRotateRightLeft(AvlNode u) {
        u.right = rotateRight(u.right);
        return rotateLeft(u);
    }

/***************************** Helper Functions ************************************/
    /**
     * Calculating the "height" of a node.
     *
     * @param cur
     * @return The height of a node (-1, if node is not existent eg. NULL).
     */
    private static int height(AvlNode cur) {
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
            return 1 + Math.max(height(cur.left), height(cur.right));
        }
    }

    private static int setBalance(AvlNode cur) {
        return cur.balance = height(cur.right) - height(cur.left);
    }


    public void setRoot(AvlNode root) {
        this.root = root;
    }

    public int getCount() {
        return root != null ? root.getCount() : 0;
    }

    private class DepthFirstIterator implements Iterator<Pair<PSObject, PSObject>> {

        private Stack<AvlNode> fringe = new Stack<AvlNode>();

        public DepthFirstIterator() {
            if (root != null) {
                fringe.push(root);
            }
        }

        public boolean hasNext() {
            return !fringe.empty();
        }

        public Pair<PSObject, PSObject> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("tree ran out of elements");
            }
            AvlNode node = fringe.pop();
            if (node.right != null) {
                fringe.push(node.right);
            }
            if (node.left != null) {
                fringe.push(node.left);
            }
            return new Pair<PSObject, PSObject>(node.key, node.value);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}


