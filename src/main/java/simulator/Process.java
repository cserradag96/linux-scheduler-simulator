package simulator;
import java.lang.Math;
import com.google.gson.Gson;

public class Process extends PriorityPolicy implements Comparable<Process> {
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
        priority = setPriority();
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Process fromJson(String proc) {
        Gson gson = new Gson();
        return gson.fromJson(proc, Process.class);
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

    public synchronized void updateVRuntime() {
        vruntime += (runCicles * NICE_0_VALUE) / loadWeight();
        runCicles = 0;
    }

    public synchronized void setVRuntime(int vruntime) {
        this.vruntime = vruntime;
    }

    public synchronized int getVRuntime() {
        return vruntime;
    }

    public int getNice() {
        return prioToNice(priority);
    }

    public int getPriority() {
        return userPriority(priority);
    }

    public synchronized boolean isFinished() {
        return isZombie();
    }

    public synchronized boolean isZombie() {
        return state == "zombie";
    }

    public synchronized boolean isBlocked() {
        return state == "blocked";
    }

    public synchronized boolean isReady() {
        return state == "ready";
    }

    public synchronized boolean isRunning() {
        return state == "running";
    }

    public synchronized void setReady() {
        state = "ready";
    }

    public synchronized void setRunning() {
        state = "running";
    }

    public synchronized void setZombie() {
        state = "zombie";
    }

    public synchronized void setBlocked() {
        state = "blocked";
    }

    public int compareTo(Process proc) {
        if (this.vruntime < proc.vruntime) return -1;
        else if (this.vruntime > proc.vruntime) return 1;
        return 0;
    }

    private synchronized void updateMemory() {
        memory += (Math.random() > 0.5 ? 1 : -1) * 10;
        memory = Math.max(memory, 0);
    }

    private boolean ioProb() {
        return Math.random() * Math.random() > 0.85;
    }

    private boolean finishProb() {
        return Math.random() * Math.random() * Math.random() > 0.95;
    }

    private int setMinCicles() {
        return (int)Math.pow(2, (int)(Math.random() * 10) + (int)(Math.random() * 10));
    }

    private int loadWeight() {
        return SCHEDULER_PRIORITY_TO_WEIGHT[userPriority(priority)];
    }

    private int setPriority() {
        if (Math.random() > 0.85) return MAX_RT_PRIORITY + (int)(Math.random() * NICE_WIDTH);
        return DEFAULT_PRIORITY;
    }
}
