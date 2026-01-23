package hexlet.code;

import hexlet.code.formatters.FormatStyle;
import hexlet.code.formatters.Formatter;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Differ {

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {
        Map<String, Object> dataFile1 = Parser.parsing(readFile(filePath1), FilenameUtils.getExtension(filePath1));
        Map<String, Object> dataFile2 = Parser.parsing(readFile(filePath2), FilenameUtils.getExtension(filePath1));

        Map<String, Map<String, Object>> differencesMap = FindDifferences.getDiff(dataFile1, dataFile2);

        FormatStyle formatStyle = Formatter.selectFormatStyle(formatName);

        return formatStyle.format(differencesMap);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, "stylish");
    }

    // чтение файла
    public static String readFile(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new Exception("File '" + filePath + "' not found.");
        }
        return Files.readString(path);
    }

}
