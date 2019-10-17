package simulator;
import org.apache.commons.cli.*;

public class CLI {
    public static Options gnuOptions;
    public static CommandLineParser gnuParser;
    public static CommandLine cmd;
    public static int coresCount;
    public static int usersCount;

    private static final String CORES = "c";
    private static final String USERS = "u";

    public static void setOptions(String [] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = prepareOptions();

        try {
            CommandLine commandLine = parser.parse(prepareOptions(), args);
            coresCount = 4;
            if (commandLine.hasOption(CORES)) {
                coresCount = ((Number) commandLine.getParsedOptionValue(CORES)).intValue();
            }

            usersCount = 1;
            if (commandLine.hasOption(USERS)) {
                usersCount = ((Number) commandLine.getParsedOptionValue(USERS)).intValue();
            }
        }

        catch (ParseException e) {
            System.out.println("\n\nUnexpected error:\n\n" + e.getMessage());
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
}
