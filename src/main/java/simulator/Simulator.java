package simulator;

public class Simulator {
    public static Kernel kernel;
    public static User user;

    private static Thread kernelThread;
    private static Thread userThread;

    public static void main(String [] args) {
        try {
            kernel = new Kernel(4);
            kernelThread =  new Thread(kernel);
            kernelThread.start();

            user = new User(kernel);
            Thread userThread =  new Thread(user);
            userThread.start();
        }

        catch (Exception e) {
            System.out.println("\n\nUnexpected error:\n\n" + e.getMessage());
            System.exit(0);
        }
    }
}
