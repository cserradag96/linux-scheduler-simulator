package simulator;
import java.lang.Math;
import org.apache.commons.lang3.RandomStringUtils;

public class User implements Runnable {
    private Kernel kernel;
    private final Object lock = new Object();
    private final int delay = 50000;

    public User(Kernel kernel) {
        this.kernel = kernel;
    }

    public boolean newProcProb() {
        return Math.random() * Math.random() * Math.random() > 0.95;
    }

    public void sleep() {
        int count = delay;
        while(count > 0) { count--; }
    }

    @Override
    public void run() {
        while(true) {
            if (newProcProb()) {
                kernel.push(new Process(RandomStringUtils.randomAlphanumeric(10)));
            }

            sleep();
        }
    }
}
