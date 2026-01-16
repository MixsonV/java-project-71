package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Runnable {
    @Option(
            names = {"-f", "--format"},
            description = "Output format [default: ${DEFAULT-VALUE}]",
            defaultValue = "stylish"
    )
    private String format;

    @Parameters(
            index = "0",
            description = "Path to the first file"
    )
    private String filepath1;

    @Parameters(
            index = "1",
            description = "Path to the second file"
    )
    private String filepath2;

    public void run() {}

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

}
