package simulator;

public class Processor implements Runnable {
    public RunQueue runQueue;
    public Dispatcher dispatcher;
    public Thread dispatcherThread;
    public Kernel kernel;
    public Process currentProc;
    public long workingTime;
    public long sleepingTime;
    public int id;
    public Log log;

    public Processor(int id, Kernel kernel) {
        this.id = id;
        this.kernel = kernel;

        log = new Log();
        runQueue = new RunQueue();
        dispatcher = new Dispatcher(this);
        dispatcherThread = new Thread(dispatcher);
        workingTime = 0;
        sleepingTime = 0;
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
            if (cur == null) sleepingTime++;
            else {
                if (cur.isBlocked() || cur.isFinished()) dispatcher.wakeUp();
                else cur.run();
                workingTime++;
            }
        }
    }
}
