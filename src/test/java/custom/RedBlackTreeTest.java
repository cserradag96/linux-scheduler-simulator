package custom;
import simulator.RedBlackTree;
import simulator.Process;

public class RedBlackTreeTest {
  public static void main(String [] args) {
      System.out.println("\nTest: RedBlackTree Class\n");
      RedBlackTree tree = new RedBlackTree();

      // 1.1 Check Search: not found
      System.out.println("1.1 Search: not found");
      System.out.println(tree.search(8) == null);

      // 1.2 Check Search: found
      System.out.println("1.2 Check Search: found");
      tree.insert(new Process(8, "proc8"));
      System.out.println(tree.search(0) == 0);

      // 2.1 Check Insert
      System.out.println("2.1 Check Insert");
      tree.insert(new Process(18, "proc18"));
      tree.insert(new Process(5, "proc5"));
      tree.insert(new Process(15, "proc15"));
      tree.inorder();
      System.out.println();

      // 2.2 Check Insert: Repetition
      System.out.println("2.2 Check Insert: Repetition");
      tree.insert(new Process(8, "proc8"));
      tree.inorder();
      System.out.println();
      tree.prettyPrint();

      // 3.1 Check Delete
      System.out.println("3.1 Check Delete");
      tree.delete(0);
      tree.inorder();
      System.out.println();

      // 3.2 Check Delete: not found
      System.out.println("3.1 Check Delete: not found");
      tree.delete(100);
      tree.inorder();
      System.out.println();

      // 3.3 Check Delete: Repeated
      System.out.println("3.1 Check Delete: Repeated (only one instance is deleted)");
      tree.delete(0);
      tree.inorder();
      System.out.println();

      // 3.4 Check Delete: Repeated (again)
      System.out.println("3.1 Check Delete: Repeated (again)");
      tree.delete(0);
      tree.inorder();
      System.out.println();

      // Print Tree
      System.out.println("TREE");
      tree.prettyPrint();
  }
}
