package simulator;

public class Dispatcher implements Runnable {
    public Core core;

    private final int quantum = 42;
    private int count;
    private boolean sleeping;

    public Dispatcher(Core core) {
        this.core = core;
        count = quantum;
        sleeping = false;
    }

    public synchronized void wakeUp() {
        count = quantum;
        sleeping = false;
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

                if (!prev.isFinished()) {
                    if (prev.isBlocked()) core.kernel.io.push(prev);
                    else {
                        prev.setReady();
                        core.runQueue.push(prev);
                    }
                }

                core.kernel.gui.pushProc(prev);
            }

            else if (!core.runQueue.isEmpty()) {
                Process next = core.runQueue.pop();
                next.setRunning();
                core.setCurrent(next);
                core.kernel.gui.pushProc(next);
                sleep();
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
