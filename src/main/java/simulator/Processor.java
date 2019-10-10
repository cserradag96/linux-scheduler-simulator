package simulator;

public class Processor {
    public RedBlackTree runQueue;
    public Dispatcher dispatcher;
    public Kernel kernel;
    public Process currentProc;

    public Processor(Kernel kernel) {
        this.runQueue = new RedBlackTree();
        this.dispatcher = new Dispatcher();
        this.kernel = kernel;
        this.currentProc = null;
    }

    public void add(Process proc) {
        this.runQueue.insert(proc);
    }
}
