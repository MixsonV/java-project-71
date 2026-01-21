package hexlet.code.formatters;

import java.util.Map;

public interface FormatStyle {

    String format(Map<String, Map<String, Object>> dif) throws Exception;

}
