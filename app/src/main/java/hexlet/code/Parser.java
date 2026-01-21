package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public final class Parser {

    // парсинг файла
    public static Map<String, Object> parsing(String filePath) throws Exception {
        String content = readFile(filePath);
        String extensionName = FilenameUtils.getExtension(filePath);

        ObjectMapper mapper = switch (extensionName) {
            case "json" -> new ObjectMapper();
            case "yaml", "yml" -> new ObjectMapper(new YAMLFactory());
            default -> throw new Exception("Extension '" + extensionName + "' is not supported.");
        };
        return mapper.readValue(content, new TypeReference<>() { });
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
