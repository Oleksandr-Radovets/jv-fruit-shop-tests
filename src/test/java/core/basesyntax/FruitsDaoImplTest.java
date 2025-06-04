package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitsDaoImplTest {
    private final FruitsDao dao = new FruitsDaoImpl();

    @BeforeEach
    void setup() {
        Storage.fruitStorage.clear();
    }

    @Test
    void addFruit_shouldStoreValue() {
        dao.addFruit("apple", 50);
        assertEquals(50, dao.getAllFruits().get("apple"));
    }

    @Test
    void getAllFruits_shouldReturnMap() {
        dao.addFruit("apple", 100);
        Map<String, Integer> all = dao.getAllFruits();
        assertEquals(1, all.size());
    }

    @Test
    void addFruit_overrideExistingValue() {
        dao.addFruit("banana", 30);
        dao.addFruit("banana", 70);
        assertEquals(70, dao.getAllFruits().get("banana"));
    }

    @Test
    void addFruit_zeroQuantity_storesZero() {
        dao.addFruit("kiwi", 0);
        assertEquals(0, dao.getAllFruits().get("kiwi"));
    }

    @Test
    void addFruit_negativeQuantity_allowed() {
        dao.addFruit("grape", -25);
        assertEquals(-25, dao.getAllFruits().get("grape"));
    }

    @Test
    void getAllFruits_whenEmptyStorage_returnsEmptyMap() {
        Map<String, Integer> all = dao.getAllFruits();
        assertTrue(all.isEmpty());
    }

    @Test
    void multipleFruits_shouldStoreAllCorrectly() {
        dao.addFruit("apple", 10);
        dao.addFruit("banana", 20);
        dao.addFruit("cherry", 30);

        Map<String, Integer> all = dao.getAllFruits();
        assertEquals(3, all.size());
        assertEquals(10, all.get("apple"));
        assertEquals(20, all.get("banana"));
        assertEquals(30, all.get("cherry"));
    }
}
