package simulator;

public class Log {
    private String buf;

    public synchronized void pushProc(Core core, Process proc) {
        String buf = "";
        if (core != null) buf += String.format("\nCore %d:", core.id);
        buf += String.format("\n  Process %d:", proc.pid);
        buf += String.format("\n    Name: " + proc.name);
        buf += String.format("\n    Cicles: %d", proc.totalCicles);
        buf += String.format("\n    Memory: %d", proc.memory);
        buf += String.format("\n    I/O: %d", proc.ioRequest);
        buf += String.format("\n    State: " +  proc.state);
        buf += String.format("\n    V-Runtime: %d", proc.vruntime);
        System.out.println(buf);
    }
}
