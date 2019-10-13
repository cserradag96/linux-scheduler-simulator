package simulator;

public class Processor implements Runnable {
    public RunQueue runQueue;
    public Dispatcher dispatcher;
    public Thread dispatcherThread;
    public Kernel kernel;
    public Process currentProc;
    public long workingTime;
    public long sleepingTime;

    public Log log;
    public final Object lock = new Object();

    public Processor(Kernel kernel) {
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
    }

    public Process getCurrent() {
        return currentProc;
    }

    public void setCurrent(Process proc) {
        this.currentProc = proc;
    }

    @Override
    public void run() {
        dispatcherThread.start();

        while(true) {
            synchronized (lock) {
                Process cur = getCurrent();
                if (cur == null) sleepingTime++;
                else {
                    if (cur.isBlocked() || cur.isFinished()) dispatcher.wakeUp();
                    else cur.run();
                }
            }

            workingTime++;
        }
    }
}
