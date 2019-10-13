package simulator;

public class Log {
    private boolean writing = false;
    private String buf;

    public synchronized void pushProc(Processor core, Process proc) {
        while (writing) {
            try { wait(); }
            catch (InterruptedException e) {}
        }

        writing = true;
        String buf = "";
        buf += String.format("\nCore %d:", core.id);
        buf += String.format("\n  Process %d:", proc.pid);
        buf += String.format("\n    Name: " + proc.name);
        buf += String.format("\n    Cicles: %d", proc.totalCicles);
        buf += String.format("\n    Memory: %d", proc.memory);
        buf += String.format("\n    I/O: %d", proc.ioRequest);
        buf += String.format("\n    State: " +  proc.state);
        buf += String.format("\n    V-Runtime: %d", proc.vruntime);
        System.out.println(buf);
        writing = false;
        notify();
    }
}
