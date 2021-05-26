package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.Transaction;

public interface TransactionNode extends Node {
    Transaction execute(DataContext context) throws EvaluationException;
}
