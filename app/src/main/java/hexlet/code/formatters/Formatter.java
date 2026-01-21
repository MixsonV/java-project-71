package hexlet.code.formatters;

public final class Formatter {

    public static FormatStyle selectFormatStyle(String formatName) throws Exception {
        return switch (formatName) {
            case "stylish" -> new Stylish();
            case "json" -> new Json();
            case "yaml", "yml" -> new Yaml();
            default -> throw new Exception("Format '" + formatName + "' is not supported.");
        };
    }

}
