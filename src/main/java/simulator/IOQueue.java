package simulator;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

public class IOQueue implements Runnable {
    private Kernel kernel;
    private List<Process> queue;
;
    public IOQueue(Kernel kernel) {
        this.kernel = kernel;
        queue = new ArrayList<Process>();
    }

    public synchronized void push(Process proc) {
        queue.add(proc);
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized Process pop() {
        return queue.remove(0);
    }

    private boolean ioProb() {
        return Math.random() > 0.95;
    }

    @Override
    public void run() {
        while(true) {
            if (!isEmpty() && ioProb()) {
                Process proc = pop();
                proc.setReady();
                kernel.push(proc);
            }
        }
    }
}
