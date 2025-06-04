package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.calculationservice.FruitsServiceImpl;
import core.basesyntax.service.handler.BalanceOperation;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseOperation;
import core.basesyntax.service.handler.ReturnOperation;
import core.basesyntax.service.handler.SupplyOperation;
import core.basesyntax.service.handlerservice.HandlerService;
import core.basesyntax.service.handlerservice.HandlerServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitsServiceImplTest {
    private FruitsServiceImpl fruitsService;

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear(); // Очистити сховище перед кожним тестом

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        HandlerService handlerService = new HandlerServiceImpl(handlers);
        fruitsService = new FruitsServiceImpl(handlerService);
    }

    @Test
    void balanceOperation_shouldSetInitialValue() {
        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("apple");
        tx.setQuantity(100);
        tx.setOperation(FruitTransaction.Operation.BALANCE);

        fruitsService.processTransactions(List.of(tx));

        assertEquals(100, Storage.fruitStorage.get("apple"));
    }

    @Test
    void supplyOperation_shouldAddToExistingQuantity() {
        Storage.fruitStorage.put("banana", 20);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("banana");
        tx.setQuantity(30);
        tx.setOperation(FruitTransaction.Operation.SUPPLY);

        fruitsService.processTransactions(List.of(tx));

        assertEquals(50, Storage.fruitStorage.get("banana"));
    }

    @Test
    void purchaseOperation_shouldSubtractFromExistingQuantity() {
        Storage.fruitStorage.put("kiwi", 40);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("kiwi");
        tx.setQuantity(15);
        tx.setOperation(FruitTransaction.Operation.PURCHASE);

        fruitsService.processTransactions(List.of(tx));

        assertEquals(25, Storage.fruitStorage.get("kiwi"));
    }

    @Test
    void returnOperation_shouldAddToExistingQuantity() {
        Storage.fruitStorage.put("melon", 10);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("melon");
        tx.setQuantity(5);
        tx.setOperation(FruitTransaction.Operation.RETURN);

        fruitsService.processTransactions(List.of(tx));

        assertEquals(15, Storage.fruitStorage.get("melon"));
    }

    @Test
    void purchaseMoreThanAvailable_shouldThrowException() {
        Storage.fruitStorage.put("grape", 5);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("grape");
        tx.setQuantity(10);
        tx.setOperation(FruitTransaction.Operation.PURCHASE);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fruitsService.processTransactions(List.of(tx)));

        assertEquals("Not enough stock for purchase", exception.getMessage());
    }

    @Test
    void nullOperation_shouldThrowException() {
        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("mango");
        tx.setQuantity(10);
        tx.setOperation(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fruitsService.processTransactions(List.of(tx)));

        assertTrue(exception.getMessage().contains("No handler found"));
    }

    @Test
    void multipleTransactions_shouldProcessCorrectly() {
        List<FruitTransaction> transactions = List.of(
                createTx("apple", 100, FruitTransaction.Operation.BALANCE),
                createTx("apple", 20, FruitTransaction.Operation.SUPPLY),
                createTx("apple", 30, FruitTransaction.Operation.PURCHASE),
                createTx("apple", 10, FruitTransaction.Operation.RETURN)
        );

        fruitsService.processTransactions(transactions);

        assertEquals(100 + 20 - 30 + 10, Storage.fruitStorage.get("apple"));
    }

    private FruitTransaction createTx(String fruit, int quantity, FruitTransaction.Operation op) {
        FruitTransaction tx = new FruitTransaction();
        tx.setFruit(fruit);
        tx.setQuantity(quantity);
        tx.setOperation(op);
        return tx;
    }
}
