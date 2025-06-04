package core.basesyntax.service.parser;

import core.basesyntax.entity.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserImpl implements TransactionParser {
    private static final String INPUT_PATTERN = "\\w{1},[a-z]*,\\d+";
    private static final String ERROR_MESSAGE_TEMPLATE =
            "The string is in an incorrect format: \"%s\""
                    + ". Expected format: <operation>,<fruit name>,<quantity>";

    private static final int ACTIVITY_POSITION = 0;
    private static final int FRUIT_POSITION = 1;
    private static final int QUANTITY_POSITION = 2;

    @Override
    public List<FruitTransaction> parse(List<String> lines) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        for (String d : lines) {
            if (!d.matches(INPUT_PATTERN)) {
                throw new IllegalArgumentException(String.format(ERROR_MESSAGE_TEMPLATE, d));
            }

            String[] split = d.split(",");
            FruitTransaction fruitTransaction = new FruitTransaction();
            FruitTransaction.Operation operation = getOperation(split[ACTIVITY_POSITION]);
            String fruit = split[FRUIT_POSITION];
            int quantity = Integer.parseInt(split[QUANTITY_POSITION]);

            fruitTransaction.setOperation(operation);
            fruitTransaction.setFruit(fruit);
            fruitTransaction.setQuantity(quantity);
            fruitTransactionList.add(fruitTransaction);
        }
        return fruitTransactionList;
    }

    private FruitTransaction.Operation getOperation(String code) {
        for (FruitTransaction.Operation op : FruitTransaction.Operation.values()) {
            if (op.getOperation().equalsIgnoreCase(code)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Unknown operation: \""
                + code + "\". Check the input data.");
    }
}
