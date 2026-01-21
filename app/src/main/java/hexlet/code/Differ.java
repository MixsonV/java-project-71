package hexlet.code;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Differ {

    public static String generate(String filePath1, String filePath2, String extensionName) throws Exception {
        checkExtension(filePath1, filePath2);
        Map<String, Object> dataFile1 = Parser.parsing(readFile(filePath1), extensionName);
        Map<String, Object> dataFile2 = Parser.parsing(readFile(filePath2), extensionName);
        return diff(dataFile1, dataFile2);
    }

    public static void checkExtension(String filePath1, String filePath2) throws Exception {
        String extensionName1 = FilenameUtils.getExtension(filePath1);
        String extensionName2 = FilenameUtils.getExtension(filePath2);
        if (!extensionName1.equals(extensionName2)) {
            throw new Exception("Files have different extensions.");
        }
    }

    public static String readFile(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new Exception("File '" + filePath + "' not found.");
        }
        return Files.readString(path);
    }

    public static String diff(Map<String, Object> data1, Map<String, Object> data2) {
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
    }

    public static String formatValue(Object value) {
        if (value == null) {
            return "null";
        } else {
            return value.toString();
        }
    }

}
