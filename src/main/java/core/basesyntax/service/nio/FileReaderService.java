package core.basesyntax.service.nio;

import java.util.List;

public interface FileReaderService {
    List<String> read(String path);
}
