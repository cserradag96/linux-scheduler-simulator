package simulator;
import com.google.gson.Gson;
import java.lang.Math;

public class Process extends PriorityPolicy {
    public String name;
    public String state;

    public int pid;
    public int memory;
    public int vruntime;
    public int priority;

    private int minCicles;
    private int runCicles;
    private int ioRequest;
    private boolean running;

    public Process(String name) {
        this.pid = PidGenerator.getInstance().getUniqueId();
        this.name = name;
        this.state = "ready";
        this.memory = 0;
        // this.vruntime = (int)(Math.random() * 10);   // For test
        this.vruntime = 0;
        this.running = false;
        this.minCicles = setMinCicles();
        this.runCicles = 0;
        this.ioRequest = 0;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Process fromJson(String proc) {
        Gson gson = new Gson();
        return gson.fromJson(proc, Process.class);
    }

    public void updateVRuntime(int runtime) {
        vruntime += (runtime * NICE_0_VALUE) / loadWeight();
    }

    public void run() {
        running = true;
        while(running) {
            runCicles++;
            updateMemory();

            if (ioProb()) {
                ioRequest++;
            }

            if (finishProb() && runCicles >= minCicles) {
                running = false;
                System.out.println(String.format("\nProcess %d:", pid));
                System.out.println(String.format("  Cicles: %d", runCicles));
                System.out.println(String.format("  Memory: %d", memory));
                System.out.println(String.format("  I/O: %d", ioRequest));
            }
        }
    }

    private void updateMemory() {
        memory += (Math.random() > 0.5 ? 1 : -1) * 10;
        memory = Math.max(memory, 0);
    }

    private boolean ioProb() {
        return Math.random() * Math.random() > 0.95;
    }

    private boolean finishProb() {
        System.out.println("Yeeiii");
        return Math.random() * Math.random() * Math.random() > 0.95;
    }

    private int setMinCicles() {
        return (int)Math.pow(2, 10 + (int)(Math.random() * 10));
    }

    private int loadWeight() {
        return SCHEDULER_PRIORITY_TO_WEIGHT[userPriority(priority)];
    }
}
