package net.schimweg.financeProcessor.model;

import net.schimweg.financeProcessor.execution.EvaluationException;

public interface TransactionSet extends FinanceObject {
    Transaction current() throws EvaluationException;
    boolean next() throws EvaluationException;
}
