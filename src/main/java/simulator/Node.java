package simulator;

class Node {
    public int color;      // 1 is Red, 0 is Black
    public Node left;      // Pointer to left child
    public Node parent;    // Pointer to the parent
    public Node right;     // Pointer to right child
    public Process proc;   // Holds the key

    public Node() {
        color = 0;
        left = null;
        parent = null;
        right = null;
        proc = null;
    }

    public Node(Process proc, Node left, Node right) {
        color = 1;
        parent = null;
        this.left = left;
        this.right = right;
        this.proc = proc;
    }

    public int getKey() {
        return proc != null ? proc.vruntime : null;
    }
}
