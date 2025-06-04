package core.basesyntax.service.report;

import core.basesyntax.dao.FruitsDao;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String HEADER = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final FruitsDao fruitsDao;

    public ReportServiceImpl(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    public String reportAllFruits() {
        return fruitsDao.getAllFruits().entrySet().stream()
                .map(entry -> entry.getKey() + SEPARATOR + entry.getValue())
                .collect(Collectors.collectingAndThen(
                        Collectors.joining(LINE_SEPARATOR),
                        body -> HEADER + LINE_SEPARATOR + body
                ));
    }
}
