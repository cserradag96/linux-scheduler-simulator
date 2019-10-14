package simulator;

public class Node {
    public static final int BLACK = 0;
    public static final int RED = 1;
    public Process key;

    Node parent;
    Node left;
    Node right;
    public int numLeft = 0;
    public int numRight = 0;
    public int color;

    Node(){
        color = BLACK;
        numLeft = 0;
        numRight = 0;
        parent = null;
        left = null;
        right = null;
    }

    Node(Process key){
        this();
        this.key = key;
    }
}
