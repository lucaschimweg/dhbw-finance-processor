package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.ast.TransactionNode;
import net.schimweg.financeProcessor.ast.TransactionSetNode;
import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.Transaction;

public class MaxNode implements TransactionNode {

    public static class MaxNodeConfig {
        public TransactionSetNode source;
    }

    private final TransactionSetNode source;

    public MaxNode(MaxNode.MaxNodeConfig config) {
        this(config.source);
    }

    public MaxNode(TransactionSetNode source) {
        this.source = source;
    }

    @Override
    public Transaction execute(DataContext context) throws EvaluationException {
        var data = source.execute(context);
        Transaction max = null;

        while (data.next()) {
            if (max == null || data.current().getAmount().getValue() > max.getAmount().getValue()) {
                max = data.current();
            }
        }

        return max;
    }
}
