package simulator;

public class RunQueue extends RedBlackTree {
    public synchronized boolean isEmpty() {
        return length == 0;
    }

    public synchronized void push(Process proc) {
        if (!isEmpty()) {
            Node node = treeMinimum(root);
            int minVRuntime = node.key.getVRuntime();
            if (proc.getVRuntime() < minVRuntime) proc.setVRuntime(minVRuntime);
        }

        insert(proc);
    }

    public synchronized Process pop() {
        Node node = treeMinimum(root);
        remove(node);
        return node.key;
    }
}
