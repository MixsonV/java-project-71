package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    private static String jsonFilePath1;
    private static String jsonFilePath2;
    private static String yamlFilePath1;
    private static String yamlFilePath2;
    private static String expectedStylish;
    private static String expectedPlain;
    private static String expectedJson;

    @BeforeAll
    static void init(@TempDir Path tempDir) throws IOException, URISyntaxException {
        var jsonResourceUrl1 = DifferTest.class.getResource("/json_file_1.json");
        var jsonResourceUrl2 = DifferTest.class.getResource("/json_file_2.json");
        var yamlResourceUrl1 = DifferTest.class.getResource("/yaml_file_1.yaml");
        var yamlResourceUrl2 = DifferTest.class.getResource("/yaml_file_2.yaml");
        var expectedStylishUrl = DifferTest.class.getResource("/expected_stylish");
        var expectedPlainUrl = DifferTest.class.getResource("/expected_plain");
        var expectedJsonUrl = DifferTest.class.getResource("/expected_json");

        if (jsonResourceUrl1 == null || jsonResourceUrl2 == null || yamlResourceUrl1 == null
                || yamlResourceUrl2 == null || expectedStylishUrl == null || expectedJsonUrl == null
                || expectedPlainUrl == null) {
            throw new IllegalStateException("Test resources not found in classpath. "
                    + "Ensure they are in src/test/resources/");
        }

        Path jsonSourcePath1 = Paths.get(jsonResourceUrl1.toURI());
        Path jsonSourcePath2 = Paths.get(jsonResourceUrl2.toURI());
        Path yamlSourcePath1 = Paths.get(yamlResourceUrl1.toURI());
        Path yamlSourcePath2 = Paths.get(yamlResourceUrl2.toURI());
        Path expectedStylishSourcePath = Paths.get(expectedStylishUrl.toURI());
        Path expectedPlainSourcePath = Paths.get(expectedPlainUrl.toURI());
        Path expectedJsonSourcePath = Paths.get(expectedJsonUrl.toURI());

        // Копирование во временные файлы
        Path jsonTempFile1 = tempDir.resolve("json_file_1.json");
        Path jsonTempFile2 = tempDir.resolve("json_file_2.json");
        Path yamlTempFile1 = tempDir.resolve("yaml_file_1.yaml");
        Path yamlTempFile2 = tempDir.resolve("yaml_file_2.yaml");

        Files.copy(jsonSourcePath1, jsonTempFile1);
        Files.copy(jsonSourcePath2, jsonTempFile2);
        Files.copy(yamlSourcePath1, yamlTempFile1);
        Files.copy(yamlSourcePath2, yamlTempFile2);

        jsonFilePath1 = jsonTempFile1.toString();
        jsonFilePath2 = jsonTempFile2.toString();
        yamlFilePath1 = yamlTempFile1.toString();
        yamlFilePath2 = yamlTempFile2.toString();
        expectedStylish = Files.readString(expectedStylishSourcePath).trim();
        expectedPlain = Files.readString(expectedPlainSourcePath).trim();
        expectedJson = Files.readString(expectedJsonSourcePath).trim();
    }

    @Test
    void testGenerateWithDefaultFormat() throws Exception {
        String actualJson = Differ.generate(jsonFilePath1, jsonFilePath2);
        String actualYaml = Differ.generate(yamlFilePath1, yamlFilePath2);
        assertEquals(expectedStylish, actualJson);
        assertEquals(expectedStylish, actualYaml);
    }

    @Test
    void testGenerateWithJsonFormat() throws Exception {
        String actualJson = Differ.generate(jsonFilePath1, jsonFilePath2, "json");
        String actualYaml = Differ.generate(yamlFilePath1, yamlFilePath2, "json");
        assertEquals(expectedJson, actualJson);
        assertEquals(expectedJson, actualYaml);
    }

    @Test
    void testGenerateWithStylishFormat() throws Exception {
        String actualJson = Differ.generate(jsonFilePath1, jsonFilePath2, "stylish");
        String actualYaml = Differ.generate(yamlFilePath1, yamlFilePath2, "stylish");
        assertEquals(expectedStylish, actualJson);
        assertEquals(expectedStylish, actualYaml);
    }

    @Test
    void testGenerateWithPlainFormat() throws Exception {
        String actualJson = Differ.generate(jsonFilePath1, jsonFilePath2, "plain");
        String actualYaml = Differ.generate(yamlFilePath1, yamlFilePath2, "plain");
        assertEquals(expectedPlain, actualJson);
        assertEquals(expectedPlain, actualYaml);
    }

}
