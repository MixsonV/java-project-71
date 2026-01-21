package hexlet.code.formatters;

import java.util.Map;

public final class Stylish implements FormatStyle {

    @Override
    public String format(Map<String, Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder("{\n");

        diff.forEach((key, value) -> {
            String status = (String) value.get("status");

            String line = switch (status) {
                case "added" -> String.format("  + %s: %s%n", key, value.get("value"));
                case "removed" -> String.format("  - %s: %s%n", key, value.get("value"));
                case "updated" -> String.format("  - %s: %s%n  + %s: %s%n",
                        key, value.get("oldValue"), key, value.get("newValue"));
                default -> String.format("    %s: %s%n", key, value.get("value"));
            };
            result.append(line);
        });

        result.append("}");
        return result.toString();
    }

}
