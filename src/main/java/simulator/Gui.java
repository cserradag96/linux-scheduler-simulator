package simulator;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class Gui{
    private boolean writing = false;
    public JTable table;
    public JScrollPane jsp;
    public String columns[] = {"PID", "Name", "Cicles", "Memory", "I/O", "State", "V-Runtime"};
    public DefaultTableModel model;
    public JFrame frame;
    private int row;
    private boolean exists;

  	public Gui(){
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

  	}

    public synchronized void pushProc(Processor core, Process proc) {
        while (writing) {
            try { wait(); }
            catch (InterruptedException e) {}
        }

        writing = true;

        row = existsInTable(table, Integer.toString(proc.pid));

        if(row == -1){
          model.addRow(new Object[]{Integer.toString(proc.pid), proc.name, Long.toString(proc.totalCicles), Integer.toString(proc.memory), Integer.toString(proc.ioRequest), proc.state, Integer.toString(proc.vruntime)});
        }
        else{
          table.setValueAt(Long.toString(proc.totalCicles), row, 2);
          table.setValueAt(Integer.toString(proc.memory), row, 3);
          table.setValueAt(Integer.toString(proc.ioRequest), row, 4);
          table.setValueAt(proc.state, row, 5);
          table.setValueAt(Integer.toString(proc.vruntime), row, 6);
        }

        writing = false;
        notifyAll();
    }

    public int existsInTable(JTable table, String pid) {

        // Get row count
        int rowCount = table.getRowCount();

        // Check against all entries
        for (int i = 0; i < rowCount; i++) {
            if(pid.equals(table.getValueAt(i,0).toString())){
                return i;
            }
        }
    return -1;
    }
}