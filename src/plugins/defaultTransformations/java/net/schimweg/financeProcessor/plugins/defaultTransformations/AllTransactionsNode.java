package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.ast.TransactionSetNode;
import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.TransactionSet;

public class AllTransactionsNode implements TransactionSetNode {

    public static class AllTransactionsNodeConfig {
        public String dataSourceName;
    }

    private final String dataSourceName;

    public AllTransactionsNode(AllTransactionsNodeConfig config) {
        this(config.dataSourceName);
    }

    public AllTransactionsNode(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Override
    public TransactionSet execute(DataContext context) throws EvaluationException {
        try {
            return context.getDataProvider(dataSourceName).getAllTransactions();
        } catch (Exception e) {
            throw new EvaluationException("DataSource not found");
        }

    }

    @Override
    public String name() {
        return "ALL";
    }
}
