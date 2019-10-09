package simulator;
import com.google.gson.Gson;
import java.lang.Math;

public class Process extends PriorityPolicy {
    public String pid;
    public String name;
    public String type;
    public String state;
    public int memory;
    public int vruntime;
    public int priority;
    private boolean running;

    public Process(String pid, String name, String type, String state, int memory, int vruntime) {
      this.pid = pid;
      this.name = name;
      this.type = type;
      this.state = state;
      this.memory = memory;
      this.vruntime = vruntime;
      this.running = false;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Process fromJson(String proc) {
        Gson gson = new Gson();
        return gson.fromJson(proc, Process.class);
    }

    public int loadWeight() {
      return SCHEDULER_PRIORITY_TO_WEIGHT[userPriority(this.priority)];
    }

    public void updateVRuntime(int runtime) {
      this.vruntime += (runtime * NICE_0_VALUE) / loadWeight();
    }

    public void run() {
      this.running = true;
      while(this.running) {
        System.out.println(Math.random());
      }
    }
}
