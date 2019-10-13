package simulator;

public class Dispatcher implements Runnable {
    public Processor core;

    private final int quantum = 512;
    private int count;
    private boolean sleeping;

    public Dispatcher(Processor core) {
        this.core = core;
        count = quantum;
        sleeping = false;
    }

    public void wakeUp() {
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

                if (prev.isFinished()) core.log.pushProc(prev);
                else {
                    prev.updateVRuntime();
                    if (prev.isBlocked()) core.kernel.io.push(prev);
                    else {
                        prev.setReady();
                        core.runQueue.push(prev);
                    }
                }
            }

            if (!core.runQueue.isEmpty()) {
                Process next = core.runQueue.pop();
                next.setRunning();
                core.setCurrent(next);
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
