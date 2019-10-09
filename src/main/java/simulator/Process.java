package simulator;
import com.google.gson.Gson;

public class Process {
    public String pid;
    public String name;
    public String type;
    public String state;
    public int memory;
    public int vruntime;
    public int priority;

    public Process(String pid, String name, String type, String state, int memory, int vruntime) {
      this.pid = pid;
      this.name = name;
      this.type = type;
      this.state = state;
      this.memory = memory;
      this.vruntime = vruntime;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Process fromJson(String proc) {
        Gson gson = new Gson();
        return gson.fromJson(proc, Process.class);
    }
}
