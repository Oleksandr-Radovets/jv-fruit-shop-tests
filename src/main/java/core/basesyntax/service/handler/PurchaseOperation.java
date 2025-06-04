package core.basesyntax.service.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int current = Storage.fruitStorage.getOrDefault(fruit, 0);
        int quantity = transaction.getQuantity();
        if (quantity > current) {
            throw new IllegalArgumentException("Not enough stock for purchase");
        }
        Storage.fruitStorage.put(fruit, current - quantity);
    }
}
