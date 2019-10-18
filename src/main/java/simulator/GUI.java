package simulator;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class GUI implements Runnable {
    public JTable processTable, coresTable;
    public String processColumns[] = {"PID", "NAME", "USER", "PR", "NC", "CICLES", "MEM", "I/O", "STATE", "VIRT"};
    public String coresColumns[] = {"ID", "WORKING TIME", "SLEEPING TIME", "USAGE"};
    public DefaultTableModel processModel, coresModel;
    public JPanel panel, processPanel, coresPanel;
    public JScrollPane processScroll, coresScroll;
    public JFrame frame;

    public GUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        processPanel = new JPanel();
        processPanel.setLayout(new BorderLayout());
        processModel = new DefaultTableModel(processColumns, 0);
        processTable = new JTable(processModel);
        alignCenter(processTable, 0, 1);
        alignRight(processTable, 1, processColumns.length);
        processScroll = new JScrollPane(processTable);
        processPanel.add(processScroll, BorderLayout.CENTER);

        tabbedPane.addTab("Process", null, processPanel, "See process stats");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        coresPanel = new JPanel();
        coresPanel.setLayout(new BorderLayout());
        coresModel = new DefaultTableModel(coresColumns, 0);
        coresTable = new JTable(coresModel);
        alignCenter(coresTable, 0, 1);
        alignRight(coresTable, 1, coresColumns.length);
        coresScroll = new JScrollPane(coresTable);
        coresPanel.add(coresScroll, BorderLayout.CENTER);

        tabbedPane.addTab("Cores", null, coresPanel, "See cores stats");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        frame = new JFrame("Scheduler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 500);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public synchronized void pushProc(Process proc) {
        int row = existsInTable(processTable, Integer.toString(proc.pid));
        if (row == -1) addRow(proc);
        else updateRow(proc, row);
    }

    public void pushCore(Core core) {
        int row = existsInTable(coresTable, Integer.toString(core.id));
        if (row == -1) addRowCore(core);
        else updateRowCore(core, row);
    }

    public void addRow(Process proc) {
        processModel.addRow(
            new Object[] {
                Integer.toString(proc.pid),
                proc.name,
                proc.owner,
                proc.getPriority(),
                proc.getNice(),
                Long.toString(proc.totalCicles),
                Integer.toString(proc.memory),
                Integer.toString(proc.ioRequest),
                proc.state,
                Integer.toString(proc.vruntime)
            }
        );
    }

    public void addRowCore(Core core) {
        coresModel.addRow(
            new Object[] {
                Integer.toString(core.id),
                Long.toString(core.workingTime),
                Long.toString(core.sleepingTime),
                Long.toString(core.usagePercentage())
            }
        );
    }

    public void updateRow(Process proc, int row) {
        processTable.setValueAt(Long.toString(proc.totalCicles), row, 5);
        processTable.setValueAt(Integer.toString(proc.memory), row, 6);
        processTable.setValueAt(Integer.toString(proc.ioRequest), row, 7);
        processTable.setValueAt(proc.state, row, 8);
        processTable.setValueAt(Integer.toString(proc.getVRuntime()), row, 9);
    }

    public void updateRowCore(Core core, int row) {
        coresTable.setValueAt(Long.toString(core.workingTime), row, 1);
        coresTable.setValueAt(Long.toString(core.sleepingTime), row, 2);
        coresTable.setValueAt(Long.toString(core.usagePercentage()) + "%", row, 3);
    }

    public int existsInTable(JTable table, String pid) {
        int rowCount = table.getRowCount();

        for(int i = 0; i < rowCount; i++) {
            if (pid.equals(table.getValueAt(i,0).toString())) return i;
        }
        return -1;
    }

    private void alignRight(JTable table, int min, int max) {
        for(int i = min; i < max; i++) {
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }
    }

    private void alignCenter(JTable table, int min, int max) {
        for(int i = min; i < max; i++) {
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    @Override
    public void run() {
        while(true) {}
    }
}
