package simulator;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

public class IOQueue implements Runnable {
    private Kernel kernel;
    private List<Process> queue;

    public IOQueue(Kernel kernel) {
        this.kernel = kernel;
        queue = new ArrayList<Process>();
    }

    public void push(Process proc) {
        queue.add(proc);
    }

    public boolean ioProb() {
        return Math.random() > 0.25;
    }

    @Override
    public void run() {
        while(true) {
            if (!queue.isEmpty() && ioProb()) {
                Process proc = queue.remove(0);
                proc.setReady();
                kernel.push(proc);
            }
        }
    }
}
