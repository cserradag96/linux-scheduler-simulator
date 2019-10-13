package simulator;

public class RunQueue extends RedBlackTree {
    public boolean isEmpty() {
        return count == 0;
    }

    public synchronized void push(Process proc) {
        insert(proc);
    }

    public int length() {
        return count;
    }

    public synchronized Process pop() {
        Node node = min();
        deleteNode(node);
        return node.proc;
    }
}
