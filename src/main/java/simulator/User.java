package simulator;
import java.lang.Math;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class User implements Runnable {
    public String name;

    private Kernel kernel;
    private Timer timer;
    private final int delay = 512;

    public User(Kernel kernel, String name) {
        this.kernel = kernel;
        this.name = name;
        timer = setTimer();
    }

    public boolean createProb() {
        return Math.random() > 0.5;
    }

    public void createProc() {
        kernel.push(new Process(name));
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
        for(int i = 0; i < kernel.coresCount * 3; i++) createProc();
        timer.start();

        while(true) {}
    }
}
