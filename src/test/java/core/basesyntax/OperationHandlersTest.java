package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.BalanceOperation;
import core.basesyntax.service.handler.PurchaseOperation;
import core.basesyntax.service.handler.ReturnOperation;
import core.basesyntax.service.handler.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlersTest {
    private BalanceOperation balanceOperation;
    private PurchaseOperation purchaseOperation;
    private ReturnOperation returnOperation;
    private SupplyOperation supplyOperation;

    @BeforeEach
    public void setUp() {
        Storage.fruitStorage.clear();
        balanceOperation = new BalanceOperation();
        purchaseOperation = new PurchaseOperation();
        returnOperation = new ReturnOperation();
        supplyOperation = new SupplyOperation();
    }

    @Test
    public void balanceOperation_shouldSetBalance() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(10);

        balanceOperation.handle(transaction);
        assertEquals(10, Storage.fruitStorage.get("apple"));
    }

    @Test
    public void balanceOperation_shouldThrowException_whenNegativeQuantity() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(-5);

        assertThrows(IllegalArgumentException.class, () -> balanceOperation.handle(transaction));
    }

    @Test
    public void purchaseOperation_shouldDecreaseStock() {
        Storage.fruitStorage.put("banana", 15);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(5);

        purchaseOperation.handle(transaction);
        assertEquals(10, Storage.fruitStorage.get("banana"));
    }

    @Test
    public void purchaseOperation_shouldThrowException_whenNotEnoughStock() {
        Storage.fruitStorage.put("banana", 3);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(5);

        assertThrows(IllegalArgumentException.class, () -> purchaseOperation.handle(transaction));
    }

    @Test
    public void returnOperation_shouldIncreaseStock() {
        Storage.fruitStorage.put("orange", 7);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("orange");
        transaction.setQuantity(4);

        returnOperation.handle(transaction);
        assertEquals(11, Storage.fruitStorage.get("orange"));
    }

    @Test
    public void returnOperation_shouldThrowException_whenQuantityNonPositive() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("orange");
        transaction.setQuantity(0);

        assertThrows(IllegalArgumentException.class, () -> returnOperation.handle(transaction));
    }

    @Test
    public void supplyOperation_shouldIncreaseStock() {
        Storage.fruitStorage.put("pear", 8);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("pear");
        transaction.setQuantity(7);

        supplyOperation.handle(transaction);
        assertEquals(15, Storage.fruitStorage.get("pear"));
    }

    @Test
    public void supplyOperation_shouldThrowException_whenQuantityNonPositive() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("pear");
        transaction.setQuantity(-3);

        assertThrows(IllegalArgumentException.class, () -> supplyOperation.handle(transaction));
    }
}
