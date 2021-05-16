package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.TransactionSet;

public interface TransactionSetNode extends Node {
    TransactionSet execute(DataContext context);
}
