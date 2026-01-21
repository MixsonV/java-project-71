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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DifferTest {
    private static String jsonFilePath1;
    private static String jsonFilePath2;
    private static String yamlFilePath1;
    private static String yamlFilePath2;
    private static String jsTempFilePath;
    private static String expectedStylish;
    private static String expectedJson;
    private static String expectedYaml;

    @BeforeAll
    static void init(@TempDir Path tempDir) throws IOException, URISyntaxException {
        var jsonResourceUrl1 = DifferTest.class.getResource("/json_file_1.json");
        var jsonResourceUrl2 = DifferTest.class.getResource("/json_file_2.json");
        var yamlResourceUrl1 = DifferTest.class.getResource("/yaml_file_1.yaml");
        var yamlResourceUrl2 = DifferTest.class.getResource("/yaml_file_2.yaml");
        var jsResourceUrl = DifferTest.class.getResource("/js_file.js");
        var expectedStylishUrl = DifferTest.class.getResource("/expected_stylish");
        var expectedJsonUrl = DifferTest.class.getResource("/expected_json");
        var expectedYamlUrl = DifferTest.class.getResource("/expected_yaml");

        if (jsonResourceUrl1 == null || jsonResourceUrl2 == null || yamlResourceUrl1 == null
                || yamlResourceUrl2 == null || expectedStylishUrl == null || expectedJsonUrl == null
                || expectedYamlUrl == null || jsResourceUrl == null) {
            throw new IllegalStateException("Test resources not found in classpath. "
                    + "Ensure they are in src/test/resources/");
        }

        Path jsonSourcePath1 = Paths.get(jsonResourceUrl1.toURI());
        Path jsonSourcePath2 = Paths.get(jsonResourceUrl2.toURI());
        Path yamlSourcePath1 = Paths.get(yamlResourceUrl1.toURI());
        Path yamlSourcePath2 = Paths.get(yamlResourceUrl2.toURI());
        Path jsSourcePath = Paths.get(jsResourceUrl.toURI());
        Path expectedStylishSourcePath = Paths.get(expectedStylishUrl.toURI());
        Path expectedJsonSourcePath = Paths.get(expectedJsonUrl.toURI());
        Path expectedYamlSourcePath = Paths.get(expectedYamlUrl.toURI());

        // Копирование во временные файлы
        Path jsonTempFile1 = tempDir.resolve("json_file_1.json");
        Path jsonTempFile2 = tempDir.resolve("json_file_2.json");
        Path yamlTempFile1 = tempDir.resolve("yaml_file_1.yaml");
        Path yamlTempFile2 = tempDir.resolve("yaml_file_2.yaml");
        Path jsTempFile = tempDir.resolve("js_file.js");

        Files.copy(jsonSourcePath1, jsonTempFile1);
        Files.copy(jsonSourcePath2, jsonTempFile2);
        Files.copy(yamlSourcePath1, yamlTempFile1);
        Files.copy(yamlSourcePath2, yamlTempFile2);
        Files.copy(jsSourcePath, jsTempFile);

        jsonFilePath1 = jsonTempFile1.toString();
        jsonFilePath2 = jsonTempFile2.toString();
        yamlFilePath1 = yamlTempFile1.toString();
        yamlFilePath2 = yamlTempFile2.toString();
        jsTempFilePath = jsTempFile.toString();
        expectedStylish = Files.readString(expectedStylishSourcePath).trim();
        expectedJson = Files.readString(expectedJsonSourcePath).trim();
        expectedYaml = Files.readString(expectedYamlSourcePath).trim();
    }

    @Test
    void testGenerateWithJson() throws Exception {
        String actual = Differ.generate(jsonFilePath1, jsonFilePath2, "json").trim();
        assertEquals(expectedJson, actual);
    }

    @Test
    void testGenerateWithYaml() throws Exception {
        String actual = Differ.generate(yamlFilePath1, yamlFilePath2, "yaml").trim();
        assertEquals(expectedYaml, actual);
    }

    @Test
    void testGenerateWithStylish() throws Exception {
        String actual = Differ.generate(jsonFilePath1, jsonFilePath2, "stylish").trim();
        assertEquals(expectedStylish, actual);
    }

    @Test
    void testGenerateWithUnknownFormat() {
        Exception exception = assertThrows(
                Exception.class,
                () -> Differ.generate(jsonFilePath1, jsonFilePath2, "txt")
        );
        assertEquals("Format 'txt' is not supported.", exception.getMessage());
    }

    @Test
    void testGenerateWithUnknownExtension() {
        Exception exception = assertThrows(
                Exception.class,
                () -> Differ.generate(jsTempFilePath, jsonFilePath2, "json")
        );
        assertEquals("Extension 'js' is not supported.", exception.getMessage());
    }

    @Test
    void testReadNonExistentFile() {
        Exception exception = assertThrows(
                Exception.class,
                () -> Differ.generate("unknown_file.json", jsonFilePath2, "json")
        );
        assertEquals("File 'unknown_file.json' not found.", exception.getMessage());
    }

}
