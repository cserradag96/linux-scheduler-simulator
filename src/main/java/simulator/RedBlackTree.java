package simulator;

public class RedBlackTree {
    private Node root;
    private Node TNULL;

    private void preOrderHelper(Node node) {
        if (node != TNULL) {
            System.out.print(node.getKey() + " ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    private void inOrderHelper(Node node) {
        if (node != TNULL) {
            inOrderHelper(node.left);
            System.out.print(node.getKey() + " ");
            inOrderHelper(node.right);
        }
    }

    private void postOrderHelper(Node node) {
        if (node != TNULL) {
            postOrderHelper(node.left);
            postOrderHelper(node.right);
            System.out.print(node.getKey() + " ");
        }
    }

    private Node searchTreeHelper(Node node, int key) {
        if (node == TNULL || key == node.getKey()) {
            return node;
        }

        if (key < node.getKey()) {
            return searchTreeHelper(node.left, key);
        }
        return searchTreeHelper(node.right, key);
    }

    // fix the rb tree modified by the delete operation
    private void fixDelete(Node x) {
        Node s;
        while (x != root && x.color == 0) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == 1) {
                    // case 3.1
                    s.color = 0;
                    x.parent.color = 1;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == 0 && s.right.color == 0) {
                    // case 3.2
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.right.color == 0) {
                        // case 3.3
                        s.left.color = 0;
                        s.color = 1;
                        rightRotate(s);
                        s = x.parent.right;
                    }

                    // case 3.4
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.right.color = 0;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == 1) {
                    // case 3.1
                    s.color = 0;
                    x.parent.color = 1;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == 0 && s.right.color == 0) {
                    // case 3.2
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.left.color == 0) {
                        // case 3.3
                        s.right.color = 0;
                        s.color = 1;
                        leftRotate(s);
                        s = x.parent.left;
                    }

                    // case 3.4
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.left.color = 0;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = 0;
    }

    private void rbTransplant(Node u, Node v){
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left){
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private void deleteNodeHelper(Node node, int key) {
        // find the node containing key
        Node z = TNULL;
        Node x, y;
        while (node != TNULL){
            if (node.getKey() == key) {
                z = node;
            }

            if (node.getKey() <= key) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (z == TNULL) {
            System.out.println("Couldn't find key in the tree");
            return;
        }

        y = z;
        int yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0){
            fixDelete(x);
        }
    }

    // fix the red-black tree
    private void fixInsert(Node k){
        Node u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left; // uncle
                if (u.color == 1) {
                    // case 3.1
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        // case 3.2.2
                        k = k.parent;
                        rightRotate(k);
                    }
                    // case 3.2.1
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right; // uncle

                if (u.color == 1) {
                    // mirror case 3.1
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        // mirror case 3.2.2
                        k = k.parent;
                        leftRotate(k);
                    }
                    // mirror case 3.2.1
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    private void printHelper(Node root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != TNULL) {
           System.out.print(indent);
           if (last) {
              System.out.print("R----");
              indent += "     ";
           } else {
              System.out.print("L----");
              indent += "|    ";
           }

           String sColor = root.color == 1?"RED":"BLACK";
           System.out.println(root.getKey() + "(" + sColor + ")");
           printHelper(root.left, indent, false);
           printHelper(root.right, indent, true);
        }
    }

    public RedBlackTree() {
        TNULL = new Node();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    // Pre-Order traversal
    // Node . Left Subtree . Right Subtree
    public void preorder() {
        preOrderHelper(this.root);
    }

    // In-Order traversal
    // Left Subtree . Node . Right Subtree
    public void inorder() {
        inOrderHelper(this.root);
    }

    // Post-Order traversal
    // Left Subtree . Right Subtree . Node
    public void postorder() {
        postOrderHelper(this.root);
    }

    // search the tree for the key k
    // and return the corresponding node
    public Node searchNode(int k) {
        return searchTreeHelper(this.root, k);
    }

    // search the tree for the key k
    public Integer search(int k) {
        Node node = searchTreeHelper(this.root, k);
        if(node == TNULL) {
            return null;
        }
        return node.getKey();
    }

    // find the node with the minimum key
    public Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    // find the node with the maximum key
    public Node maximum(Node node) {
        while (node.right != TNULL) {
            node = node.right;
        }
        return node;
    }

    // find the successor of a given node
    public Node successor(Node x) {
        // if the right subtree is not null,
        // the successor is the leftmost node in the
        // right subtree
        if (x.right != TNULL) {
            return minimum(x.right);
        }

        // else it is the lowest ancestor of x whose
        // left child is also an ancestor of x.
        Node y = x.parent;
        while (y != TNULL && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    // find the predecessor of a given node
    public Node predecessor(Node x) {
        // if the left subtree is not null,
        // the predecessor is the rightmost node in the
        // left subtree
        if (x.left != TNULL) {
            return maximum(x.left);
        }

        Node y = x.parent;
        while (y != TNULL && x == y.left) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    // rotate left at node x
    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // rotate right at node x
    public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // insert the key to the tree in its appropriate position
    // and fix the tree
    public void insert(Process proc) {
        // Ordinary Binary Search Insertion
        Node node = new Node(proc, TNULL, TNULL);
        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.getKey() < x.getKey()) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        // y is parent of x
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.getKey() < y.getKey()) {
            y.left = node;
        } else {
            y.right = node;
        }

        // if new node is a root node, simply return
        if (node.parent == null){
            node.color = 0;
            return;
        }

        // if the grandparent is null, simply return
        if (node.parent.parent == null) {
            return;
        }

        // Fix the tree
        fixInsert(node);
    }

    public Node getRoot(){
        return this.root;
    }

    // delete the node from the tree
    public void delete(int key) {
        deleteNodeHelper(this.root, key);
    }

    // print the tree structure on the screen
    public void prettyPrint() {
        printHelper(this.root, "", true);
    }
}
