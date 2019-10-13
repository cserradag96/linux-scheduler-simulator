package simulator;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

public class IOQueue implements Runnable {
    private List<Process> queue;
    private Kernel kernel;
    private final int delay = 5;

    public IOQueue(Kernel kernel) {
        queue = new ArrayList<Process>();
        this.kernel = kernel;
    }

    public void add(Process proc) {
        queue.add(proc);
    }

    public boolean ioProb() {
        return Math.random() * Math.random() > 0.85;
    }

    @Override
    public void run() {
        while(true) {
            if (!queue.isEmpty() && ioProb()) {
                // Llamar al dispatcher para que lleve el proceso al runqueue
                System.out.println("I/O");
            }

            try {
                Thread.sleep(delay);
            }
            catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
