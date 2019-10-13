package simulator;

public class Dispatcher implements Runnable {
    public Processor core;
    public RunQueue queue;

    private final int quantum = 512;
    private int count;
    private boolean sleeping;

    public Dispatcher(Processor core, RunQueue queue) {
        this.core = core;
        this.queue = queue;
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

                if (!prev.isFinished()) {
                    prev.setReady();
                    prev.updateVRuntime();
                    queue.push(prev);
                    prev = null;
                }
            }

            if (!queue.isEmpty()) {
                Process next = queue.pop();
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
