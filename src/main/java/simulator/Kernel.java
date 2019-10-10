package simulator;
import java.util.List;
import java.util.ArrayList;

public class Kernel {
    public int coresCount;
    public IOQueue ioQueue;
    public List<Processor> cores;

    public Kernel(int coresCount) {
        this.coresCount = coresCount;
        this.ioQueue = new IOQueue();
        this.cores = new ArrayList<Processor>();

        for(int i = 0; i < coresCount; i++) {
            cores.add(new Processor(this));
        }

        Process proc0 = new Process(0, "proc0");
        Process proc1 = new Process(1, "proc1");
        Process proc2 = new Process(2, "proc2");
        Process proc3 = new Process(3, "proc3");
        proc0.run();
        proc1.run();
        proc2.run();
        proc3.run();
    }
}
