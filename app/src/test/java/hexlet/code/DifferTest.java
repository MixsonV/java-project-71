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
    private static String expectedDiff;

    @BeforeAll
    static void init(@TempDir Path tempDir) throws IOException, URISyntaxException{
        var jsonResourceUrl1 = DifferTest.class.getResource("/json_file_1.json");
        var jsonResourceUrl2 = DifferTest.class.getResource("/json_file_2.json");
        var yamlResourceUrl1 = DifferTest.class.getResource("/yaml_file_1.yaml");
        var yamlResourceUrl2 = DifferTest.class.getResource("/yaml_file_2.yaml");
        var resourceExpectedUrl = DifferTest.class.getResource("/expected_stylish.txt");

        if (jsonResourceUrl1 == null || jsonResourceUrl2 == null || yamlResourceUrl1 == null ||
                yamlResourceUrl2 == null || resourceExpectedUrl == null) {
            throw new IllegalStateException("Test resources not found in classpath. " +
                    "Ensure they are in src/test/resources/");
        }

        Path jsonResource1 = Paths.get(jsonResourceUrl1.toURI());
        Path jsonResource2 = Paths.get(jsonResourceUrl2.toURI());
        Path yamlResource1 = Paths.get(yamlResourceUrl1.toURI());
        Path yamlResource2 = Paths.get(yamlResourceUrl2.toURI());
        Path resourceExpected = Paths.get(resourceExpectedUrl.toURI());

        // Копирование во временные файлы
        Path jsonFile1 = tempDir.resolve("json_file_1.json");
        Path jsonFile2 = tempDir.resolve("json_file_2.json");
        Path yamlFile1 = tempDir.resolve("yaml_file_1.yaml");
        Path yamlFile2 = tempDir.resolve("yaml_file_2.yaml");

        Files.copy(jsonResource1, jsonFile1);
        Files.copy(jsonResource2, jsonFile2);
        Files.copy(yamlResource1, yamlFile1);
        Files.copy(yamlResource2, yamlFile2);

        jsonFilePath1 = jsonFile1.toString();
        jsonFilePath2 = jsonFile2.toString();
        yamlFilePath1 = yamlFile1.toString();
        yamlFilePath2 = yamlFile2.toString();
        expectedDiff = Files.readString(resourceExpected).trim();
    }

    @Test
    void testGenerateWithJson() throws Exception {
        String actual = Differ.generate(jsonFilePath1, jsonFilePath2, "json").trim();
        assertEquals(expectedDiff, actual);
    }

    @Test
    void testGenerateWithYaml() throws Exception {
        String actual = Differ.generate(yamlFilePath1, yamlFilePath2, "yaml").trim();
        assertEquals(expectedDiff, actual);
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
    void testReadNonExistentFile() {
        Exception exception = assertThrows(
                Exception.class,
                () -> Differ.generate("unknown_file.json", jsonFilePath2, "json")
        );
        assertEquals("File 'unknown_file.json' not found.", exception.getMessage());
    }

    @Test
    void testCheckExtension() {
        Exception exception = assertThrows(
                Exception.class,
                () -> Differ.checkExtension(jsonFilePath1, yamlFilePath2)
        );
        assertEquals("Files have different extensions.", exception.getMessage());
    }

}
