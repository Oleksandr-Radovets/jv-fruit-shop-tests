package core.basesyntax.service.handler;

import core.basesyntax.entity.FruitTransaction;

public interface OperationHandler {
    void handle(FruitTransaction transaction);
}
