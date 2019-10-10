package simulator;

public class Node {
    private static Node NIL = null;

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

    public Node(Process proc) {
        color = 1;
        parent = nil();
        this.left = nil();
        this.right = nil();
        this.proc = proc;
    }

    public static Node nil() {
        if (NIL == null) NIL = new Node();
        return NIL;
    }

    public int getKey() {
        return proc != null ? proc.vruntime : null;
    }
}
