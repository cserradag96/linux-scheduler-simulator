package simulator;

public class RunQueue extends RedBlackTree {
    public synchronized void add(Process proc) {
        insert(proc);
    }
}
