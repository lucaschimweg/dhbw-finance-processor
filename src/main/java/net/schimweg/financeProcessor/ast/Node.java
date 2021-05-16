package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.FinanceObject;
import net.schimweg.financeProcessor.model.Transaction;

public interface Node {
    String name();
    FinanceObject execute(DataContext context);
}
