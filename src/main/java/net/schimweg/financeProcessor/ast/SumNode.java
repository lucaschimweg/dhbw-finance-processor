package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.model.Amount;
import net.schimweg.financeProcessor.model.Currency;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.TransactionDirection;

public class SumNode implements AmountNode {

    public static class SumNodeConfig {
        public TransactionSetNode source;
        public boolean respectDirection = true;
    }

    private final TransactionSetNode source;
    private final boolean respectDirection;

    public SumNode(SumNodeConfig config) {
        this(config.source, config.respectDirection);
    }

    public SumNode(TransactionSetNode source, boolean respectDirection) {
        this.source = source;
        this.respectDirection = respectDirection;
    }

    public Amount execute(DataContext context) {
        long amount = 0;
        var data = source.execute(context);

        while (data.next()) {
            if (!respectDirection) {
                amount += data.current().getAmount().getValue();
            } else {
                amount += data.current().getAmount().getValue() *
                        ((data.current().getDirection() == TransactionDirection.INCOMING) ? 1 : -1);
            }

        }

        return new Amount(amount, Currency.EUR);
    }

    public String name() {
        return "SUM";
    }
}
