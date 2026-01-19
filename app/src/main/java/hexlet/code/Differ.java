package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Differ {
    public static void generate(String filePath1, String filePath2) throws Exception {
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

}
