package simulator;

public class Log {
    private boolean write = false;
    private String buf;

    public synchronized void pushProc(Process proc) {
        while (write) {
            try { wait(); }
            catch (InterruptedException e) {}
        }

        write = true;
        String buf = "";
        buf += String.format("\nProcess %d:", proc.pid);
        buf += String.format("\n  Cicles: %d", proc.totalCicles);
        buf += String.format("\n  Memory: %d", proc.memory);
        buf += String.format("\n  I/O: %d", proc.ioRequest);
        buf += String.format("\n  State: " +  proc.state);
        buf += String.format("\n  V-Runtime: %d", proc.vruntime);
        System.out.println(buf);
        write = false;
        notify();
    }
}
