package simulator;

public class Dispatcher implements Runnable {
    public Processor core;

    private final int quantum = 128;
    private int count;
    private boolean sleeping;
    public final Object lock = new Object();

    public Dispatcher(Processor core) {
        this.core = core;
        count = quantum;
        sleeping = false;
    }

    public void wakeUp() {
        synchronized (lock) {
            count = quantum;
            sleeping = false;
        }
    }

    public void sleep() {
        sleeping = true;
        while(count > 0) { count--; }
        wakeUp();
    }

    public void contextChange() {
        if (!sleeping) {
            Process prev = core.getCurrent();

            if (prev != null) {
                core.setCurrent(null);
                prev.updateVRuntime();

                if (prev.isFinished()) core.log.pushProc(core, prev);
                else if (prev.isBlocked()) core.kernel.io.push(prev);
                else {
                    prev.setReady();
                    core.runQueue.push(prev);
                }
            }

            synchronized (lock) {
                if (!core.runQueue.isEmpty()) {
                    Process next = core.runQueue.pop();
                    if (next != null) {
                        next.setRunning();
                        core.setCurrent(next);
                        sleep();
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while(true) {
            contextChange();
        }
    }
}
