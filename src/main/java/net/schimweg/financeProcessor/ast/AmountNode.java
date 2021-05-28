package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.Amount;
import net.schimweg.financeProcessor.model.DataContext;

/**
 * A Node returning an Amount on execution
 */
public interface AmountNode extends Node {
    /**
     * Executes the AmountNode to gather its return value
     * @param context The data context for this execution
     * @return The Node's return value
     * @throws EvaluationException Thrown when anything goes wrong in the calculation
     */
    @Override
    Amount execute(DataContext context) throws EvaluationException;
}
