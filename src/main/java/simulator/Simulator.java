package simulator;
import org.apache.commons.cli.*;

public class Simulator extends CLI {
    public static Kernel kernel;
    public static User [] users;

    private static Thread kernelThread;
    private static Thread [] usersThreads;

    public static void main(String[] args) throws ParseException {
        setOptions(args);
        start();
    }

    public static void start() {
        try {
            kernel = new Kernel(coresCount);
            kernelThread = new Thread(kernel);
            kernelThread.start();

            users = new User[usersCount];
            usersThreads = new Thread[usersCount];

            for(int i = 0; i < usersCount; i++) {
                users[i] = new User(kernel, "user" + Integer.toString(i));
                usersThreads[i] = new Thread(users[i]);
                usersThreads[i].start();
            }
        }

        catch (Exception e) {
            System.out.println("\n\nUnexpected error:\n\n" + e.getMessage());
            System.exit(0);
        }
    }
}
