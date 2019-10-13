package simulator;

public class RunQueue extends RedBlackTree {
    private final Object lock = new Object();

    public boolean isEmpty() {
        return count == 0;
    }

    public void push(Process proc) {
        synchronized (lock) {
            insert(proc);
        }
    }

    public int length() {
        return count;
    }

    public Process pop() {
        synchronized (lock) {
            Node node = min();
            deleteNode(node);
            return node.proc;
        }
    }
}
