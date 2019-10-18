package simulator;
import java.lang.Math;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.apache.commons.lang3.RandomStringUtils;

public class User implements Runnable {
    private Kernel kernel;
    private Timer timer;
    private final int delay = 1024;
    private final int strLen = 10;

    public User(Kernel kernel) {
        this.kernel = kernel;
        timer = setTimer();
    }

    public boolean createProb() {
        return Math.random() > 0.95;
    }

    public void createProc() {
        kernel.push(new Process(RandomStringUtils.randomAlphanumeric(strLen)));
    }

    public Timer setTimer() {
        return new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();                     // ZA WARUDO! TOKI YO TOMARE!
                if (createProb()) createProc();   // WRYYYYY!
                timer.start();                    // TOKI WA UGOKIDASU
            }
        });
    }

    @Override
    public void run() {
        for(int i = 0; i < kernel.coresCount * 2; i++) createProc();
        timer.start();

        while(true) {}
    }
}
