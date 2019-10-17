package simulator;
import java.io.PrintWriter;
import org.apache.commons.cli.*;

public class CLI {
    public static Options options;
    public static CommandLineParser parser;
    public static CommandLine cmd;
    public static int coresCount;
    public static int usersCount;

    private static final String CORES = "c";
    private static final String USERS = "u";

    public static void setOptions(String [] args) {
        parser = new DefaultParser();
        options = prepareOptions();

        try {
            CommandLine cmd = parser.parse(prepareOptions(), args);
            coresCount = 4;
            if (cmd.hasOption(CORES)) {
                coresCount = ((Number) cmd.getParsedOptionValue(CORES)).intValue();
            }

            usersCount = 1;
            if (cmd.hasOption(USERS)) {
                usersCount = ((Number) cmd.getParsedOptionValue(USERS)).intValue();
            }
        }

        catch (ParseException e) {
            System.out.println("\n\nUnexpected error:\n\n" + e.getMessage());
            printUsage();
            System.exit(0);
        }
    }

    private static Options prepareOptions() {
        Options options = new Options();
        options
            .addOption(getCoresOption())
            .addOption(getUsersOption());
        return options;
    }

    private static Option getCoresOption() {
        return Option.builder(CORES).desc("number of cores, 4 by default")
            .longOpt("cores")
            .type(Number.class)
            .hasArg()
            .build();
    }

    private static Option getUsersOption() {
        return Option.builder(USERS).desc("number of users, 1 by default")
            .longOpt("cores")
            .type(Number.class)
            .hasArg()
            .build();
    }

    private static void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter writer = new PrintWriter(System.out);
        formatter.printUsage(writer, 80, "simulator", options);
        writer.flush();
    }
}
