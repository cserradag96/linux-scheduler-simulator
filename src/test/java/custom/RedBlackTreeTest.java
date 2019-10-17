package custom;
import simulator.RedBlackTree;
import simulator.Process;
import simulator.Node;

public class RedBlackTreeTest {
    public static void main(String [] args) {
        System.out.println("\nTest: RedBlackTree Class\n");
        RedBlackTree tree = new RedBlackTree();
        System.out.println(tree.size());

        Process xd = new Process("xD");
        tree.insert(xd);
        System.out.println(tree.length);
        tree.remove(tree.treeMinimum(tree.root));
        // tree.remove(tree.treeMinimum(tree.root));
        System.out.println(tree.length);
    }
}
