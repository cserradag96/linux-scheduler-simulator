package simulator;
import com.google.gson.Gson;

public class Process {
    public String pid;
    public String name;
    public String type;
    public String state;
    public int memory;
    public int execTime;

    public Process(String pid, String name, String type, String state, int memory, int execTime) {
      this.pid = pid;
      this.name = name;
      this.type = type;
      this.state = state;
      this.memory = memory;
      this.execTime = execTime;
    }

    public static String toJson(Process proc) {
        Gson gson = new Gson();
        return gson.toJson(proc);
    }

    public static Process fromJson(String proc) {
        Gson gson = new Gson();
        return gson.fromJson(proc, Process.class);
    }
}
