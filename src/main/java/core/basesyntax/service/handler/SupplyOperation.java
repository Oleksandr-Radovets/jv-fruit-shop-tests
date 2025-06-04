package core.basesyntax.service.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int quantity = transaction.getQuantity();

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive for "
                    + "supply operations.");
        }

        String fruit = transaction.getFruit();
        int current = Storage.fruitStorage.getOrDefault(fruit, 0);
        Storage.fruitStorage.put(fruit, current + quantity);
    }
}
