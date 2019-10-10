package simulator;
import java.util.List;
import java.util.ArrayList;

public class Kernel implements Runnable {
    public int coresCount;
    public IOQueue ioQueue;
    private static Processor [] cores;
    private static Thread [] coresThread;

    public Kernel(int coresCount) {
        this.coresCount = coresCount;
        cores = new Processor[coresCount];
        coresThread = new Thread[coresCount];
        ioQueue = new IOQueue();

        for(int i = 0; i < coresCount; i++) {
            cores[i] = new Processor(this);
            coresThread[i] = new Thread(cores[i]);
        }
    }

    public void run() {
        for(int i = 0; i < coresCount; i++) {
            coresThread[i].start();
        }

        // Add startup process here
        cores[0].add(new Process("proc0"));
        cores[0].add(new Process("proc1"));
        cores[0].add(new Process("proc2"));
        cores[0].add(new Process("proc3"));
    }
}
