package simulator;

public class RunQueue extends RedBlackTree {
    private boolean writing = false;

    public boolean isEmpty() {
        return count == 0;
    }

    public synchronized void push(Process proc) {
        while (writing) {
            try { wait(); }
            catch (InterruptedException e) {}
        }

        writing = true;
        insert(proc);
        writing = false;
        notify();
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
