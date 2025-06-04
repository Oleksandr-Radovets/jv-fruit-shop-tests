package core.basesyntax.service.calculationservice;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handlerservice.HandlerService;
import java.util.List;

public class FruitsServiceImpl implements FruitService {
    private final HandlerService handlerService;

    public FruitsServiceImpl(HandlerService handlerService) {
        this.handlerService = handlerService;
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = handlerService.getHandler(transaction.getOperation());
            if (handler == null) {
                throw new RuntimeException("Handler not found for operation: "
                        + transaction.getOperation());
            }
            handler.handle(transaction);
        }
    }
}

