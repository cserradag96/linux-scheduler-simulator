package simulator;

public class Kernel implements Runnable {
    public int coresCount;
    private static Thread ioThread;
    private static Processor [] cores;
    private static Thread [] coresThread;

    public Kernel(int coresCount) {
        this.coresCount = coresCount;
        cores = new Processor[coresCount];
        coresThread = new Thread[coresCount];
        ioThread = new Thread(new IOQueue(this));

        for(int i = 0; i < coresCount; i++) {
            cores[i] = new Processor(this);
            coresThread[i] = new Thread(cores[i]);
        }
    }

    @Override
    public void run() {
        // Start processors
        for(int i = 0; i < coresCount; i++) {
            coresThread[i].start();
        }

        // Start I/O
        ioThread.start();

        // Add startup process here
        cores[0].push(new Process("proc0"));
        cores[0].push(new Process("proc1"));
        cores[0].push(new Process("proc2"));
        cores[0].push(new Process("proc3"));
    }
}
