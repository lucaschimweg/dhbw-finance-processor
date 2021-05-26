package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.FinanceObject;

public interface Node {
    FinanceObject execute(DataContext context) throws EvaluationException;
}
