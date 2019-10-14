package simulator;

public class RunQueue extends RedBlackTree {
    private boolean writing = false;

    public synchronized boolean isEmpty() {
        while (writing) {
            try { wait(); }
            catch (InterruptedException e) {}
        }

        notify();
        return size() <= 1;
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

    public synchronized Process pop() {
        while (writing) {
            try { wait(); }
            catch (InterruptedException e) {}
        }

        writing = true;
        Node node = treeMinimum(root);
        remove(node);
        writing = false;
        notify();
        return node.key;
    }
}
