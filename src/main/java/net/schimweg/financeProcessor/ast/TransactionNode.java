package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.Transaction;

public interface TransactionNode {
    Transaction execute(DataContext context);
}
