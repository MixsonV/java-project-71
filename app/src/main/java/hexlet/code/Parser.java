package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public final class Parser {

    // парсинг файла
    public static Map<String, Object> parsing(String content, String format) throws Exception {
        ObjectMapper mapper = switch (format) {
            case "json" -> new ObjectMapper();
            case "yaml", "yml" -> new ObjectMapper(new YAMLFactory());
            default -> throw new Exception("Format '" + format + "' is not supported.");
        };
        return mapper.readValue(content, new TypeReference<>() { });
    }

}
