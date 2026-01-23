package hexlet.code.formatters;

import java.util.Map;

public final class Stylish implements FormatStyle {

    @Override
    public String format(Map<String, Map<String, Object>> diff) throws Exception {
        StringBuilder result = new StringBuilder("{\n");

        for (Map.Entry<String, Map<String, Object>> entry : diff.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> value = entry.getValue();
            String status = (String) value.get("status");

            String line = switch (status) {
                case "added" -> String.format("  + %s: %s%n", key, value.get("value"));
                case "removed" -> String.format("  - %s: %s%n", key, value.get("value"));
                case "updated" -> String.format("  - %s: %s%n  + %s: %s%n",
                        key, value.get("oldValue"), key, value.get("newValue"));
                case "unchanged" -> String.format("    %s: %s%n", key, value.get("value"));
                default -> throw new Exception("Status '" + status + "' is not supported.");
            };
            result.append(line);
        }

        result.append("}");
        return result.toString();
    }

}
