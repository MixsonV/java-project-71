package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DifferTest {
    private static String file1Path;
    private static String file2Path;
    private static String expectedDiff;

    @BeforeAll
    static void init(@TempDir Path tempDir) throws Exception {
        Path resource1 = Paths.get(DifferTest.class.getResource("/file1.json").toURI());
        Path resource2 = Paths.get(DifferTest.class.getResource("/file2.json").toURI());
        Path resourceExpected = Paths.get(DifferTest.class.getResource("/expected_stylish.txt").toURI());

        // Копирование во временные файлы
        Path file1 = tempDir.resolve("file1.json");
        Path file2 = tempDir.resolve("file2.json");

        Files.copy(resource1, file1);
        Files.copy(resource2, file2);

        file1Path = file1.toString();
        file2Path = file2.toString();
        expectedDiff = Files.readString(resourceExpected).trim();
    }

    @Test
    void testJsonDiff() throws Exception {
        String actual = Differ.jsonDiff(file1Path, file2Path).trim();
        assertEquals(expectedDiff, actual);
    }

    @Test
    void testGenerateWithJson() throws Exception {
        String actual = Differ.generate(file1Path, file2Path, "json").trim();
        assertEquals(expectedDiff, actual);
    }

    @Test
    void testGenerateWithUnknownFormat() {
        Exception exception = assertThrows(
                Exception.class,
                () -> Differ.generate(file1Path, file2Path, "txt")
        );
        assertEquals("Unknown format 'txt'.", exception.getMessage());
    }

    @Test
    void testReadNonExistentFile() {
        Exception exception = assertThrows(
                Exception.class,
                () -> Differ.generate("unknown_file.json", file2Path, "json")
        );
        assertEquals("Error reading file: File 'unknown_file.json' not found.", exception.getMessage());
    }
}
