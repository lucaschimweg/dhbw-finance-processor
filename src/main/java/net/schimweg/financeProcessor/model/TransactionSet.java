package net.schimweg.financeProcessor.model;

import net.schimweg.financeProcessor.execution.EvaluationException;

/**
 * Represents an iterator over a set of Transactions. No specific order is required.
 */
public interface TransactionSet extends FinanceObject {
    /**
     * Returns the currently selected transaction. Will return null before next() is called first
     * @return The currently selected transaction
     * @throws EvaluationException Thrown when something goes wrong during the evaluation
     */
    Transaction current() throws EvaluationException;

    /**
     * Selects the next transaction in the set
     * @return If the operation was successful. When false is returned no more transactions are available
     * @throws EvaluationException Thrown when something goes wrong during the evaluation
     */
    boolean next() throws EvaluationException;
}
