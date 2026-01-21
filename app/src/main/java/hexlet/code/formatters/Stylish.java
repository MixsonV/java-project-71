package hexlet.code.formatters;

import java.util.Map;

public final class Stylish implements FormatStyle {

    @Override
    public String format(Map<String, Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder("{\n");

        diff.forEach((key, value) -> {
            String status = (String) value.get("status");

            switch (status) {
                case "added" -> result.append(String.format("  + %s: %s%n", key, value.get("value")));
                case "removed" -> result.append(String.format("  - %s: %s%n", key, value.get("value")));
                case "updated" -> result.append(String.format("  - %s: %s%n  + %s: %s%n",
                        key, value.get("oldValue"), key, value.get("newValue")));
                case "unchanged" -> result.append(String.format("    %s: %s%n", key, value.get("value")));
                default -> throw new IllegalArgumentException("Unknown status: " + status);
            }
        });

        result.append("}");
        return result.toString();
    }

}
