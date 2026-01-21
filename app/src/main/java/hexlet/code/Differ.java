package hexlet.code;

import hexlet.code.formatters.FormatStyle;
import hexlet.code.formatters.Formatter;

import java.util.Map;

public class Differ {

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {
        Map<String, Object> dataFile1 = Parser.parsing(filePath1);
        Map<String, Object> dataFile2 = Parser.parsing(filePath2);

        Map<String, Map<String, Object>> differencesMap = FindDifferences.getDiff(dataFile1, dataFile2);

        FormatStyle formatStyle = Formatter.selectFormatStyle(formatName);

        return formatStyle.format(differencesMap);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, "stylish");
    }
}
