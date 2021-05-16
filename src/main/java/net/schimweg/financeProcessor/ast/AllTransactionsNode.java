package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.TransactionSet;

public class AllTransactionsNode implements TransactionSetNode {
    @Override
    public TransactionSet execute(DataContext context) {
        return context.getDataProvider().getAllTransactions();
    }

    @Override
    public String name() {
        return "ALL";
    }
}
