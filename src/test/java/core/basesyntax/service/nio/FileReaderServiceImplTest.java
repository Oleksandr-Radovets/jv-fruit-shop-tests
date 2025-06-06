package core.basesyntax.service.nio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private FileReaderService fileReaderService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void read_existingFile_shouldReturnLines() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".txt");
        List<String> expectedLines = List.of("line1", "line2", "line3");
        Files.write(tempFile, expectedLines);

        List<String> actualLines = fileReaderService.read(tempFile.toString());

        assertEquals(expectedLines, actualLines);

        Files.deleteIfExists(tempFile);
    }

    @Test
    void read_nonExistingFile_shouldThrowRuntimeException() {
        String invalidPath = "non_existing_file.txt";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReaderService.read(invalidPath));

        assertTrue(exception.getMessage().contains("could not be found"));
    }

    @Test
    void read_fileAccessIssue_shouldThrowRuntimeException() {
        String invalidPath = "some_file_that_definitely_does_not_exist.txt";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReaderService.read(invalidPath));

        assertTrue(exception.getMessage().contains("could not be found"));
    }
}
