package simulator;
import java.util.ArrayList;
import java.util.List;

public class Kernel implements Runnable {
    private int index;
    public int coresCount;
    public static GUI gui;
    public static IOQueue io;
    public static Core [] cores;

    private static Thread guiThread;
    private static Thread ioThread;
    private static Thread [] coresThread;

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
    }
}
