package simulator;

public class RunQueue extends RedBlackTree {
    private boolean writing = false;

    public synchronized boolean isEmpty() {
        return length == 0;
    }

    public synchronized void push(Process proc) {
        insert(proc);
    }

    public synchronized Process pop() {
        Node node = treeMinimum(root);
        remove(node);
        return node.key;
    }
}
