package simulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Dispatcher implements Runnable {
    public Processor core;
    public Timer timer;

    private final int quantum = 42;

    public Dispatcher(Processor core) {
        this.core = core;
        this.timer = setTimer();
    }

    public Timer setTimer() {
        return new Timer(quantum, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                contextChange();
            }
        });
    }

    public void contextChange() {

    }

    @Override
    public void run() {

    }
}
