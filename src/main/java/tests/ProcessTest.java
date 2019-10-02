package tests;
import simulator.Process;

public class ProcessTest {
    public static void main(String [] args) {
        System.out.println("\nTest: Process Class\n");

        // Test toJson method
        Process xd = new Process("xd", "0", "i/o", "zombie", 42, 23);
        xd.toJson(xd);
    }
}
