package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {
    @Option(
            names = {"-f", "--format"},
            paramLabel = "format",
            description = "Output format [default: ${DEFAULT-VALUE}]",
            defaultValue = "stylish"
    )
    private String format;

    @Parameters(
            index = "0",
            paramLabel = "filepath1",
            description = "Path to the first file"
    )
    private String filePath1;

    @Parameters(
            index = "1",
            paramLabel = "filepath2",
            description = "Path to the second file"
    )
    private String filePath2;

    @Override
    public Integer call() throws Exception {
        try {
            String resultDiff = Differ.generate(filePath1, filePath2, format);
            System.out.println(resultDiff);
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

}
