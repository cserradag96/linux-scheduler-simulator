package simulator;

public class PidGenerator {
    private static PidGenerator instance = null;
    private int nextPid = 0;

    public static PidGenerator getInstance() {
        if (instance == null) {
            instance = new PidGenerator();
        }

        return instance;
    }

    public int getUniqueId() {
        if (nextPid < 0) {
            throw new IllegalStateException("Out of IDs!");
        }

        int pid = nextPid;
        nextPid++;

        return pid;
    }
}
