package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public final class Parser {

    public static Map<String, Object> parsing(String content, String type) throws Exception {
        ObjectMapper mapper = switch (type) {
            case "json" -> new ObjectMapper();
            case "yaml", "yml" -> new ObjectMapper(new YAMLFactory());
            default -> throw new Exception("Format '" + type + "' is not supported.");
        };
        return mapper.readValue(content, new TypeReference<>() { });
    }

}
