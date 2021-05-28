package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.TransactionSet;

/**
 * A Node returning a TransactionSet on execution
 */
public interface TransactionSetNode extends Node {
    /**
     * Executes the TransactionSet to gather its return value
     * @param context The data context for this execution
     * @return The Node's return value
     * @throws EvaluationException Thrown when anything goes wrong in the calculation
     */
    @Override
    TransactionSet execute(DataContext context) throws EvaluationException;
}
