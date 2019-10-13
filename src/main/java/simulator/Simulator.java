package simulator;

public class Simulator {
    public static void main(String [] args) {
        Thread kernel =  new Thread(new Kernel(4));
        kernel.start();
    }
}
