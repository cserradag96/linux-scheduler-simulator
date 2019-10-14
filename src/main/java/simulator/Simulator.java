package simulator;

public class Simulator {
    public Kernel kernel;
    public User user;

    public static void main(String [] args) {
        try {
            Kernel kernel = new Kernel(4);
            Thread kernelThread =  new Thread(kernel);
            kernelThread.start();

            User user = new User(kernel);
            Thread userThread =  new Thread(user);
            userThread.start();
        }

        catch (Exception e) {
            System.out.println("Unexpected error");
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
