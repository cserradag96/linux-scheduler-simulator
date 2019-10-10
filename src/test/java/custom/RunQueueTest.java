package custom;
import simulator.RunQueue;
import simulator.Process;
import simulator.Node;

public class RunQueueTest {
  public static void main(String [] args) {
      System.out.println("\nTest: RunQueue Class\n");
      RunQueue tree = new RunQueue();

      // 1.1 Check Search: not found
      System.out.println("1.1 Search: not found");
      System.out.println(tree.search(8) == null);

      // 1.2 Check Search: found
      System.out.println("1.2 Check Search: found");
      Process proc0 = new Process("proc0");
      Node node0 = tree.insert(proc0);
      System.out.println(tree.search(proc0.vruntime) == node0.getKey());

      // 2.1 Check Insert
      System.out.println("2.1 Check Insert");
      Process proc1 = new Process("proc1");
      Process proc2 = new Process("proc2");
      Process proc3 = new Process("proc3");
      Node node1 = tree.insert(proc1);
      Node node2 = tree.insert(proc2);
      Node node3 = tree.insert(proc3);
      tree.inorder();
      System.out.println();

      // 2.2 Check Insert: Repetition
      System.out.println("2.2 Check Insert: Repetition");
      tree.insert(proc3);
      tree.inorder();
      System.out.println();
      tree.prettyPrint();

      // 3.1 Check Delete
      System.out.println("3.1 Check Delete");
      tree.delete(node0.getKey());
      tree.inorder();
      System.out.println();

      // 3.2 Check Delete: not found
      System.out.println("3.1 Check Delete: not found");
      tree.delete(100);
      tree.inorder();
      System.out.println();

      // 3.3 Check Delete: Repeated
      System.out.println("3.1 Check Delete: Repeated (only one instance is deleted)");
      tree.delete(node0.getKey());
      tree.inorder();
      System.out.println();

      // 3.4 Check Delete: Repeated (again)
      System.out.println("3.1 Check Delete: Repeated (again)");
      tree.delete(node0.getKey());
      tree.inorder();
      System.out.println();

      // Print Tree
      System.out.println("TREE");
      tree.prettyPrint();
  }
}
