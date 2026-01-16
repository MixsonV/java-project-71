package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
//import picocli.CommandLine.Option;
//import picocli.CommandLine.Parameters;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Runnable {
    public void run() {}

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

}