package simulator;

public class Core implements Runnable {
    public RunQueue runQueue;
    public Dispatcher dispatcher;
    public Thread dispatcherThread;
    public Kernel kernel;
    public Process currentProc;
    public long workingTime;
    public long sleepingTime;
    public int id;

    public Core(int id, Kernel kernel) {
        this.id = id;
        this.kernel = kernel;

        runQueue = new RunQueue();
        dispatcher = new Dispatcher(this);
        dispatcherThread = new Thread(dispatcher);
        workingTime = 0;
        sleepingTime = 0;

        kernel.gui.pushCore(this);
    }

    public Long usagePercentage() {
        if (workingTime + sleepingTime == 0) return 0L;
        return ((100 * workingTime) / (workingTime + sleepingTime));
    }

    public void push(Process proc) {
        runQueue.push(proc);
        kernel.gui.pushProc(proc);
    }

    public synchronized Process getCurrent() {
        return currentProc;
    }

    public synchronized void setCurrent(Process proc) {
        this.currentProc = proc;
    }

    @Override
    public void run() {
        dispatcherThread.start();

        while(true) {
            Process cur = getCurrent();
            if (cur != null) {
                if (cur.isBlocked() || cur.isFinished()) dispatcher.wakeUp();
                else cur.run();
                workingTime++;
            }
            else if (runQueue.isEmpty()) sleepingTime++;
            kernel.gui.pushCore(this);
        }
    }
}
