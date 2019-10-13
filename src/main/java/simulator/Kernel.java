package simulator;

public class Kernel implements Runnable {
    private int index;
    public int coresCount;
    public static IOQueue io;
    public static Processor [] cores;

    private static Thread ioThread;
    private static Thread [] coresThread;
    private final Object lock = new Object();

    public Kernel(int coresCount) {
        this.coresCount = coresCount;

        index = 0;
        cores = new Processor[coresCount];
        coresThread = new Thread[coresCount];
        io = new IOQueue(this);
        ioThread = new Thread(io);

        for(int i = 0; i < coresCount; i++) {
            cores[i] = new Processor(this);
            coresThread[i] = new Thread(cores[i]);
        }
    }

    public void push(Process proc) {
        cores[0].push(proc);

        synchronized(lock) {
            index = (index + 1) % coresCount;
        }
    }

    @Override
    public void run() {
        // Start I/O
        ioThread.start();

        // Start processors
        for(int i = 0; i < coresCount; i++) {
            coresThread[i].start();
        }

        // Add startup process here
        push(new Process("proc0"));
        push(new Process("proc1"));
        push(new Process("proc2"));
        push(new Process("proc3"));
        push(new Process("proc4"));
        push(new Process("proc5"));
        push(new Process("proc6"));
        push(new Process("proc7"));
    }
}
