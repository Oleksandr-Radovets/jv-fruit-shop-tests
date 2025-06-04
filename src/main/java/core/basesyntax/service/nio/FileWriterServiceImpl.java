package core.basesyntax.service.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void write(String path, List<String> lines) {
        try {
            Files.write(Path.of(path), lines);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while trying to write to the file at: "
                    + path + " . Please make sure the path is correct and you have access rights."
                    + " Reason: "
                    + e.getMessage());
        }
    }
}
