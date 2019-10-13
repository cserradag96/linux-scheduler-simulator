package simulator;

public class Simulator {
    public Kernel kernel;
    public User user;

    public static void main(String [] args) {
        Kernel kernel = new Kernel(4);
        Thread kernelThread =  new Thread(kernel);
        kernelThread.start();

        User user = new User(kernel);
        Thread userThread =  new Thread(user);
        userThread.start();
    }
}
