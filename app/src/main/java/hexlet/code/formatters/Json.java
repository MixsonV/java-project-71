package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public final class Json implements FormatStyle {

    @Override
    public String format(Map<String, Map<String, Object>> diff) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(diff);
    }

}
