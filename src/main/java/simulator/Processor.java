package simulator;

public class Processor implements Runnable {
    public RunQueue runQueue;
    public Dispatcher dispatcher;
    public Thread dispatcherThread;
    public Kernel kernel;
    public Process currentProc;
    public long workingTime;
    public long sleepingTime;

    public Processor(Kernel kernel) {
        this.kernel = kernel;

        runQueue = new RunQueue();
        dispatcher = new Dispatcher(this);
        dispatcherThread = new Thread(dispatcher);
        currentProc = null;
        workingTime = 0;
        sleepingTime = 0;
    }

    public void add(Process proc) {
        runQueue.add(proc);
    }

    @Override
    public void run() {
        dispatcherThread.start();

        while(true) {
            if (currentProc == null) {
                sleepingTime++;
                continue;
            }

            if (currentProc.finished()) dispatcher.contextChange();
            else currentProc.run();

            workingTime++;
        }
    }
}
