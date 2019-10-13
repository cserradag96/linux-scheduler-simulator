package simulator;

public class Processor implements Runnable {
    public RunQueue runQueue;
    public Dispatcher dispatcher;
    public Thread dispatcherThread;
    public Kernel kernel;
    public Process currentProc;
    public long workingTime;
    public long sleepingTime;

    private final Object lock = new Object();

    public Processor(Kernel kernel) {
        this.kernel = kernel;

        runQueue = new RunQueue();
        dispatcher = new Dispatcher(this, runQueue);
        dispatcherThread = new Thread(dispatcher);
        workingTime = 0;
        sleepingTime = 0;
    }

    public void push(Process proc) {
        runQueue.push(proc);
    }

    public Process getCurrent() {
        synchronized (lock) {
            return currentProc;
       }
    }

    public void setCurrent(Process proc) {
        synchronized (lock) {
            this.currentProc = proc;
        }
    }

    @Override
    public void run() {
        dispatcherThread.start();

        while(true) {
            synchronized (lock) {
                if (getCurrent() == null) {
                    sleepingTime++;
                    continue;
                }

                if (getCurrent().isFinished()) dispatcher.wakeUp();
                else getCurrent().run();
            }

            workingTime++;
        }
    }
}
