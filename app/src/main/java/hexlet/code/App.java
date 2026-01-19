package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

//import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Runnable {
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

    public void run() {
        switch (format) {
            case "json":
                try {
                    Map<String, Object> data1 = parseJsonFile(readFile(filePath1));
                    Map<String, Object> data2 = parseJsonFile(readFile(filePath2));

                    System.out.println("First file: " + filePath1);
                    for (Map.Entry<String, Object> entry : data1.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }

                    System.out.println("Second file: " + filePath2);
                    for (Map.Entry<String, Object> entry : data2.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return;
                }
                break;
            default:
                System.out.printf("Unknown format '%s'.%n", format);
        }
    }

    public static Map<String, Object> parseJsonFile(String content) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, new TypeReference<>() {});
        } catch (Exception e) {
            throw new Exception("Error parsing JSON file: " + e.getMessage());
        }
    }

    public static String readFile(String filePath) throws Exception {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                return Files.readString(path);
            } else {
                throw new Exception("File '" + filePath + "' not found.");
            }
        } catch (Exception e) {
            throw new Exception("Error reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

}
