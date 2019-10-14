package simulator;
import java.util.ArrayList;
import java.util.List;

public class Kernel implements Runnable {
    private int index;
    public int coresCount;
    public List<Process> procs;
    public static IOQueue io;
    public static Processor [] cores;

    private static Thread ioThread;
    private static Thread [] coresThread;
    private boolean writing = false;

    public Kernel(int coresCount) {
        this.coresCount = coresCount;

        index = 0;
        procs = new ArrayList<Process>();
        cores = new Processor[coresCount];
        coresThread = new Thread[coresCount];
        io = new IOQueue(this);
        ioThread = new Thread(io);

        for(int i = 0; i < coresCount; i++) {
            cores[i] = new Processor(i, this);
            coresThread[i] = new Thread(cores[i]);
        }
    }

    public synchronized void push(Process proc) {
        while (writing) {
            try { wait(); }
            catch (InterruptedException e) {}
        }

        writing = true;
        procs.add(proc);
        cores[index].push(proc);
        index = (index + 1) % coresCount;
        writing = false;
        notify();
    }

    @Override
    public void run() {
        // Start I/O
        ioThread.start();

        // Start processors
        for(int i = 0; i < coresCount; i++) {
            coresThread[i].start();
        }
    }
}
