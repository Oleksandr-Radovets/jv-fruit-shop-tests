package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.nio.FileWriterService;
import core.basesyntax.service.nio.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();
    private Path tempFile;

    @AfterEach
    void tearDown() throws IOException {
        if (tempFile != null && Files.exists(tempFile)) {
            Files.delete(tempFile);
        }
    }

    @Test
    void write_validPath_shouldWriteLinesSuccessfully() throws IOException {
        tempFile = Files.createTempFile("testFileWriter", ".txt");
        List<String> lines = List.of("line1", "line2", "line3");

        fileWriterService.write(tempFile.toString(), lines);

        List<String> readLines = Files.readAllLines(tempFile);
        assertEquals(lines, readLines);
    }

    @Test
    void write_invalidPath_shouldThrowRuntimeException() {
        String invalidPath = System.getProperty("java.io.tmpdir");

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileWriterService.write(invalidPath, List.of("test line")));

        assertTrue(exception.getMessage().contains("An error occurred while trying to write"));
    }
}
