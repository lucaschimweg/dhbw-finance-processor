package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.Transaction;

/**
 * A Node returning a Transaction on execution
 */
public interface TransactionNode extends Node {
    /**
     * Executes the TransactionNode to gather its return value
     * @param context The data context for this execution
     * @return The Node's return value
     * @throws EvaluationException Thrown when anything goes wrong in the calculation
     */
    @Override
    Transaction execute(DataContext context) throws EvaluationException;
}
