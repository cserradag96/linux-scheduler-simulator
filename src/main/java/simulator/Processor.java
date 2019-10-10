package simulator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Processor implements Runnable {
    private final int quantum = 42;

    public RunQueue runQueue;
    public Dispatcher dispatcher;
    public Kernel kernel;
    public Process currentProc;
    public Timer timer;

    public Processor(Kernel kernel) {
        this.runQueue = new RunQueue();
        this.dispatcher = new Dispatcher();
        this.kernel = kernel;
        this.currentProc = null;
        this.timer = setTimer();
    }

    public void add(Process proc) {
        this.runQueue.insert(proc);
    }

    public Timer setTimer() {
        return new Timer(quantum, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al dispatcher para que haga el cambio de contexto
                System.out.println("HA HA HA!");
                timer.stop();
            }
        });
    }

    public void run() {
        timer.start();
        while(true) {}
    }
}
