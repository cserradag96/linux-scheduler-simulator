package simulator;
import java.util.List;
import java.util.ArrayList;

public class Kernel {
    public int coresCount;
    public IOQueue ioQueue;
    public List<Processor> cores;

    public Kernel(int coresCount) {
        this.coresCount = coresCount;
        ioQueue = new IOQueue();
        cores = new ArrayList<Processor>();

        for(int i = 0; i < coresCount; i++) {
            cores.add(new Processor(this));
        }

        cores.get(0).add(new Process("proc0"));
        cores.get(0).add(new Process("proc1"));
        cores.get(0).add(new Process("proc2"));
        cores.get(0).add(new Process("proc3"));
    }
}
