package simulator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;

public class Kernel implements Runnable {
    private int index;
    public int coresCount;
    public static GUI gui;
    public static IOQueue io;
    public static Core [] cores;

    private static Thread guiThread;
    private static Thread ioThread;
    private static Thread [] coresThread;

    private final int quantum = 256;
    private final int strLen = 10;

    public Kernel(int coresCount) {
        this.coresCount = coresCount;

        index = 0;
        gui = new GUI();
        guiThread = new Thread(gui);
        cores = new Core[coresCount];
        coresThread = new Thread[coresCount];
        io = new IOQueue(this);
        ioThread = new Thread(io);

        for(int i = 0; i < coresCount; i++) {
            cores[i] = new Core(i, this);
            coresThread[i] = new Thread(cores[i]);
        }
    }

    public synchronized void push(Process proc) {
        cores[index].push(proc);
        index = (index + 1) % coresCount;
    }

    @Override
    public void run() {
        guiThread.start();
        ioThread.start();

        for(int i = 0; i < coresCount; i++) {
            coresThread[i].start();
        }

        while(true) {
            for(int i = 0; i < coresCount; i++) {
                if (cores[i].runQueue.isEmpty()) {
                    cores[i].push(new Process(RandomStringUtils.randomAlphanumeric(strLen)));
                }
            }

            sleep();
        }
    }

    private void sleep() {
        int count = quantum;
        while(count > 0) { count--; }
    }
}
