package custom;
import simulator.Process;

public class ProcessTest {
    public static void main(String [] args) {
        System.out.println("\nTest: Process Class\n");

        // Test toJson method
        System.out.println("toJson:");
        Process proc0 = new Process(0, "proc0");
        System.out.println(proc0.toJson());

        // Test fromJson method
        System.out.println("\nfromJson:");
        String procStr = "{'pid': 0,'name':'proc0'}";
        Process proc1 = Process.fromJson(procStr);
        System.out.println(proc1.toJson());
    }
}
