package core.basesyntax.service.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.entity.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionParserImplTest {
    private TransactionParser parser;

    @BeforeEach
    void setUp() {
        parser = new TransactionParserImpl();
    }

    @Test
    void parse_validTransactions_success() {
        List<String> lines = List.of("b,apple,100", "p,apple,20");
        List<FruitTransaction> result = parser.parse(lines);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());
    }

    @Test
    void parse_emptyInput_returnsEmptyList() {
        List<FruitTransaction> result = parser.parse(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void parse_allOperationsSupported_correctParsing() {
        List<String> lines = List.of(
                "b,apple,100",
                "s,apple,50",
                "p,apple,20",
                "r,apple,10"
        );

        List<FruitTransaction> result = parser.parse(lines);

        assertEquals(4, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(2).getOperation());
        assertEquals(FruitTransaction.Operation.RETURN, result.get(3).getOperation());
    }

    @Test
    void parse_validInput_shouldReturnCorrectList() {
        List<String> input = List.of(
                "b,apple,10",
                "s,banana,20",
                "p,orange,5",
                "r,grape,3"
        );

        List<FruitTransaction> result = parser.parse(input);

        assertEquals(4, result.size());

        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals("banana", result.get(1).getFruit());
        assertEquals(20, result.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(2).getOperation());
        assertEquals("orange", result.get(2).getFruit());
        assertEquals(5, result.get(2).getQuantity());

        assertEquals(FruitTransaction.Operation.RETURN, result.get(3).getOperation());
        assertEquals("grape", result.get(3).getFruit());
        assertEquals(3, result.get(3).getQuantity());
    }

    @Test
    void parse_invalidFormat_shouldThrowException() {
        List<String> input = List.of("x,apple,10");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(input));

        assertTrue(exception.getMessage().contains("Unknown operation"));
    }

    @Test
    void parse_invalidPattern_shouldThrowException() {
        List<String> input = List.of("b,apple,ten");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(input));

        assertTrue(exception.getMessage().contains("The string is in an incorrect format"));
    }

    @Test
    void parse_emptyInput_shouldReturnEmptyList() {
        List<FruitTransaction> result = parser.parse(List.of());

        assertTrue(result.isEmpty());
    }
}
