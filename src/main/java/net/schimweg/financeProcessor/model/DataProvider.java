package net.schimweg.financeProcessor.model;

import net.schimweg.financeProcessor.execution.EvaluationException;

/**
 * A data provider fetches Transactions from a data source. It allows to fetch independent TransactionSets.
 */
public interface DataProvider {
    /**
     * Returns a new TransactionSet, containing the provider's transactions
     * @return The new TransactionSet
     * @throws EvaluationException Thrown when something goes wrong in the fetching of the transactions
     */
    TransactionSet getAllTransactions() throws EvaluationException;
}
