package simulator;
import java.lang.Math;
import com.google.gson.Gson;

public class Process extends PriorityPolicy {
    public String name;
    public String state;
    public int vruntime;

    public int pid;
    public int memory;
    public int priority;

    public int ioRequest;
    public int minCicles;
    public long runCicles;
    public long totalCicles;

    public Process(String name) {
        this.name = name;

        pid = PidGenerator.instance().getPid();
        state = "ready";
        memory = 0;
        vruntime = 0;
        minCicles = setMinCicles();
        runCicles = 0;
        totalCicles = 0;
        ioRequest = 0;
        priority = 120;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Process fromJson(String proc) {
        Gson gson = new Gson();
        return gson.fromJson(proc, Process.class);
    }

    public void updateVRuntime() {
        vruntime += (runCicles * NICE_0_VALUE) / loadWeight();
        runCicles = 0;
    }

    public void run() {
        totalCicles++;
        runCicles++;
        updateMemory();

        if (ioProb()) {
            setBlocked();
            ioRequest++;
        }

        if (finishProb() && totalCicles >= minCicles) {
            setZombie();
        }
    }

    public boolean isFinished() {
        return isZombie();
    }

    public boolean isZombie() {
        return state == "zombie";
    }

    public boolean isBlocked() {
        return state == "blocked";
    }

    public boolean isReady() {
        return state == "ready";
    }

    public boolean isRunning() {
        return state == "running";
    }

    public void setReady() {
        state = "ready";
    }

    public void setRunning() {
         state = "running";
    }

    public void setZombie() {
        state = "zombie";
    }

    public void setBlocked() {
        state = "blocked";
    }

    private void updateMemory() {
        memory += (Math.random() > 0.5 ? 1 : -1) * 10;
        memory = Math.max(memory, 0);
    }

    private boolean ioProb() {
        return Math.random() * Math.random() > 0.95;
    }

    private boolean finishProb() {
        return Math.random() * Math.random() * Math.random() > 0.95;
    }

    private int setMinCicles() {
        return (int)Math.pow(2, (int)(Math.random() * 10));
    }

    private int loadWeight() {
        return SCHEDULER_PRIORITY_TO_WEIGHT[userPriority(priority)];
    }
}
