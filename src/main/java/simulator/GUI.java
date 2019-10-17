package simulator;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class GUI {
    private boolean writing = false;
    public JTable table, table2;
    public JScrollPane jsp, jsp2;
    public String columns[] = {"PID", "Name", "Cicles", "Memory", "I/O", "State", "V-Runtime"};
    public String columns2[] = {"ID", "Working TIme", "Sleeping Time", "Usage Percentage"};
    public DefaultTableModel model, model2;
    public JFrame frame, frame2;
    private int row;
    private boolean exists;

    public GUI(){
        frame = new JFrame();
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        jsp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setBorder(BorderFactory.createTitledBorder ("Process Simulator"));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(700,400);
        frame.add(jsp, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Cores table
        frame2 = new JFrame();
        model2 = new DefaultTableModel(columns2, 0);
        table2 = new JTable(model2);

        jsp2 = new JScrollPane(table2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp2.setBorder(BorderFactory.createTitledBorder ("Cores Simulator"));

        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setLayout(new BorderLayout());
        frame2.setSize(700,400);
        frame2.add(jsp2, BorderLayout.CENTER);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
    }

    public synchronized void pushProc(Process proc) {
        row = existsInTable(table, Integer.toString(proc.pid));
        if (row == -1) addRow(proc);
        else updateRow(proc);
    }

    public synchronized void pushCore(Core core) {
        int row = existsInTable(table2, Integer.toString(core.id));
        if (row == -1) addRowCore(core, row);
        else updateRowCore(core, row);
    }

    public void addRow(Process proc) {
        model.addRow(
            new Object[] {
                Integer.toString(proc.pid),
                proc.name,
                Long.toString(proc.totalCicles),
                Integer.toString(proc.memory),
                Integer.toString(proc.ioRequest),
                proc.state,
                Integer.toString(proc.vruntime)
            }
        );
    }

    public void addRowCore(Core core, int row) {
        model2.addRow(
            new Object[] {
                Integer.toString(core.id),
                Long.toString(core.workingTime),
                Long.toString(core.sleepingTime),
                Long.toString(core.usagePercentage())
            }
        );
    }

    public void updateRow(Process proc) {
        table.setValueAt(Long.toString(proc.totalCicles), row, 2);
        table.setValueAt(Integer.toString(proc.memory), row, 3);
        table.setValueAt(Integer.toString(proc.ioRequest), row, 4);
        table.setValueAt(proc.state, row, 5);
        table.setValueAt(Integer.toString(proc.getVRuntime()), row, 6);
    }

    public void updateRowCore(Core core, int row) {
        table2.setValueAt(Long.toString(core.workingTime), row, 1);
        table2.setValueAt(Long.toString(core.sleepingTime), row, 2);
        table2.setValueAt(Long.toString(core.usagePercentage()) + "%", row, 3);
    }

    public int existsInTable(JTable table, String pid) {
        int rowCount = table.getRowCount();

        for(int i = 0; i < rowCount; i++) {
            if (pid.equals(table.getValueAt(i,0).toString())) return i;
        }
        return -1;
    }
}
