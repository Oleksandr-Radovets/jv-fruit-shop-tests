package core.basesyntax.service.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Balance quantity cannot be negative");
        }
        Storage.fruitStorage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
