package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.FinanceObject;

/**
 * Represents a single node in an abstract syntax tree
 */
public interface Node {
    /**
     * Executes the Node to gather its return value
     * @param context The data context for this execution
     * @return The Node's return value
     * @throws EvaluationException Thrown when anything goes wrong in the calculation
     */
    FinanceObject execute(DataContext context) throws EvaluationException;
}
