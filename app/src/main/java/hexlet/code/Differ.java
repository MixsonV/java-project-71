package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Differ {
    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {
        switch (formatName) {
            case "json":
                return jsonDiff(filePath1, filePath2);
            default:
                throw new Exception("Unknown format '" + formatName + "'.");
        }
    }

    public static String jsonDiff(String filePath1, String filePath2) throws Exception {
        Map<String, Object> data1 = parseJsonFile(readFile(filePath1));
        Map<String, Object> data2 = parseJsonFile(readFile(filePath2));

        try {
            // Получаем все ключи из обоих данных, без повторений
            Set<String> allKeys = new TreeSet<>();
            allKeys.addAll(data1.keySet());
            allKeys.addAll(data2.keySet());

            StringBuilder result = new StringBuilder("{\n");

            for (String key : allKeys) {
                // Проверяем наличие ключа в обоих data
                boolean keyInData1 = data1.containsKey(key);
                boolean keyInData2 = data2.containsKey(key);
                // Получаем значения для ключа из обоих data
                String value1 = formatValue(data1.get(key));
                String value2 = formatValue(data2.get(key));

                if (keyInData1 && keyInData2) {
                    if (value1.equals(value2)) {
                        // Без изменений. Значения совпадают.
                        result.append(String.format("    %s: %s%n", key, value1));
                    } else {
                        // Изменение. Значения различаются.
                        result.append(String.format("  - %s: %s%n", key, value1));
                        result.append(String.format("  + %s: %s%n", key, value2));
                    }
                } else if (keyInData1) {
                    // Удаление. Ключ есть только в data1.
                    result.append(String.format("  - %s: %s%n", key, value1));
                } else {
                    // Добавление. Ключ есть только в data2.
                    result.append(String.format("  + %s: %s%n", key, value2));
                }
            }
            result.append("}");
            return result.toString();
        } catch (Exception e) {
            throw new Exception("Error generating JSON diff: " + e.getMessage());
        }
    }

    public static String formatValue(Object value) {
        if (value == null) {
            return "null";
        } else {
            return value.toString();
        }
    }

    public static Map<String, Object> parseJsonFile(String content) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, new TypeReference<>() { });
        } catch (Exception e) {
            throw new Exception("Error parsing JSON file: " + e.getMessage());
        }
    }

    public static String readFile(String filePath) throws Exception {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                throw new Exception("File '" + filePath + "' not found.");
            }
            return Files.readString(path);
        } catch (Exception e) {
            throw new Exception("Error reading file: " + e.getMessage());
        }
    }

}
