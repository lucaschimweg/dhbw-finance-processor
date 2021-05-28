package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.ast.AmountNode;
import net.schimweg.financeProcessor.ast.TransactionSetNode;
import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.Amount;
import net.schimweg.financeProcessor.model.DataContext;

public class CountNode implements AmountNode {

    public static class CountNodeConfig {
        public TransactionSetNode source;
    }

    private final TransactionSetNode source;

    public CountNode(CountNode.CountNodeConfig config) {
        this(config.source);
    }

    public CountNode(TransactionSetNode source) {
        this.source = source;
    }

    @Override
    public Amount execute(DataContext context) throws EvaluationException {
        var data = source.execute(context);
        long count = 0;

        while (data.next()) {
            ++count;
        }

        return new Amount(count, "");
    }
}
