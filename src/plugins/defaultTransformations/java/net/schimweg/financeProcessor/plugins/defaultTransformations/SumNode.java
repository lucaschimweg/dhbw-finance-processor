package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.ast.AmountNode;
import net.schimweg.financeProcessor.ast.TransactionSetNode;
import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.Amount;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.TransactionDirection;

public class SumNode implements AmountNode {

    public static class SumNodeConfig {
        public TransactionSetNode source;
        public boolean respectDirection = true;
    }

    private final TransactionSetNode source;
    private final boolean respectDirection;
    private String currency = "";

    public SumNode(SumNodeConfig config) {
        this(config.source, config.respectDirection);
    }

    public SumNode(TransactionSetNode source, boolean respectDirection) {
        this.source = source;
        this.respectDirection = respectDirection;
    }

    @Override
    public Amount execute(DataContext context) throws EvaluationException {
        long amount = 0;
        var data = source.execute(context);

        while (data.next()) {
            if (currency.equals("")) {
                currency = data.current().getAmount().getCurrency();
            } else {
                if (!data.current().getAmount().getCurrency().equals(currency)) {
                    throw new EvaluationException("Inconsistent currencies");
                }
            }
            if (!respectDirection) {
                amount += data.current().getAmount().getValue();
            } else {
                amount += data.current().getAmount().getValue() *
                        ((data.current().getDirection() == TransactionDirection.INCOMING) ? 1 : -1);
            }

        }

        return new Amount(amount, currency);
    }
}
