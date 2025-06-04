package core.basesyntax.service.nio;

import java.util.List;

public interface FileWriterService {
    void write(String path, List<String> lines);
}
