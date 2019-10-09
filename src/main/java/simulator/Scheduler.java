package simulator;
import java.util.List;
import java.util.ArrayList;

public class Scheduler {
    public static void main(String [] args) {
        int coresCount = 4;

        RedBlackTree readyQueue = new RedBlackTree();
        IOQueue io = new IOQueue();

        List<Processor> cores = new ArrayList<Processor>();
        for(int i = 0; i < coresCount; i++) {
            cores.add(new Processor());
        }

        Process proc0 = new Process("proc0", "0", "i/o", "zombie", 42, 23);
        proc0.run();
    }
}
