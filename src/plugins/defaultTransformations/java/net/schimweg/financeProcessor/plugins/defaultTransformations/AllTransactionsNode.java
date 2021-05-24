package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.ast.TransactionSetNode;
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
    public TransactionSet execute(DataContext context) {
        return context.getDataProvider(dataSourceName).getAllTransactions();
    }

    @Override
    public String name() {
        return "ALL";
    }
}
