package net.schimweg.financeProcessor.model;

import net.schimweg.financeProcessor.execution.EvaluationException;

public interface DataProvider {
    TransactionSet getAllTransactions() throws EvaluationException;
}
