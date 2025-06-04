package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.report.ReportService;
import core.basesyntax.service.report.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private ReportService reportService;
    private FruitsDao fruitsDao;

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
        fruitsDao = new FruitsDaoImpl();
        reportService = new ReportServiceImpl(fruitsDao);
    }

    @Test
    void reportAllFruits_emptyStorage_shouldReturnOnlyHeader() {
        String expected = "fruit,quantity" + LINE_SEPARATOR;
        String actual = reportService.reportAllFruits();

        assertEquals(expected, actual);
    }

    @Test
    void reportAllFruits_withMultipleFruits_shouldReturnCorrectReport() {
        fruitsDao.addFruit("apple", 10);
        fruitsDao.addFruit("banana", 5);
        fruitsDao.addFruit("orange", 7);

        String expectedHeader = "fruit,quantity" + LINE_SEPARATOR;
        String report = reportService.reportAllFruits();

        assertTrue(report.startsWith(expectedHeader));

        assertTrue(report.contains("apple,10"));
        assertTrue(report.contains("banana,5"));
        assertTrue(report.contains("orange,7"));

        long linesCount = report.lines().count();
        assertEquals(4, linesCount);
    }
}
