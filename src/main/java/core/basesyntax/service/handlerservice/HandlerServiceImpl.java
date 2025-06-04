package core.basesyntax.service.handlerservice;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.OperationHandler;
import java.util.Map;

public class HandlerServiceImpl implements HandlerService {
    private final Map<FruitTransaction.Operation, OperationHandler> handlers;

    public HandlerServiceImpl(Map<FruitTransaction.Operation,
            OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandler getHandler(FruitTransaction.Operation activity) {
        OperationHandler handler = handlers.get(activity);
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for operation: " + activity);
        }
        return handler;
    }
}
