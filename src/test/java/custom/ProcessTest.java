package custom;
import simulator.Process;

public class ProcessTest {
    public static void main(String [] args) {
        System.out.println("\nTest: Process Class\n");

        // Test toJson method
        System.out.println("toJson:");
        Process proc0 = new Process("proc0", "0", "i/o", "zombie", 42, 23);
        System.out.println(proc0.toJson());

        // Test fromJson method
        System.out.println("\nfromJson:");
        String procStr = "{'pid':'proc1','name':'0','type':'i/o','state':'zombie','memory':42,'execTime':23}";
        Process proc1 = Process.fromJson(procStr);
        System.out.println(proc1.toJson());
    }
}
