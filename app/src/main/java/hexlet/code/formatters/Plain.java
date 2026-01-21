package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public final class Plain implements FormatStyle {
    @Override
    public String format(Map<String, Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder();

        diff.forEach((key, value) -> {
            String status = (String) value.get("status");

            String line = switch (status) {
                case "added" -> String.format("Property '%s' was added with value: %s\n",
                        key, formatValue(value.get("value")));
                case "removed" -> String.format("Property '%s' was removed\n", key);
                case "updated" -> String.format("Property '%s' was updated. From %s to %s\n",
                        key, formatValue(value.get("oldValue")), formatValue(value.get("newValue")));
                case "unchanged" -> "";
                default -> throw new IllegalArgumentException("Unknown status: " + status);
            };
            result.append(line);
        });

        return result.toString().trim();
    }

    private static String formatValue(Object value) {
        if (value instanceof Map || value instanceof List) {
            return "[complex value]";
        } else if (value instanceof String) {
            return String.format("'%s'", value);
        }
        return String.valueOf(value);
    }
}
