package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public final class Yaml implements FormatStyle {
    @Override
    public String format(Map<String, Map<String, Object>> diff) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.writeValueAsString(diff);
    }
}
