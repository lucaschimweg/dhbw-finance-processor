package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.model.Amount;
import net.schimweg.financeProcessor.model.DataContext;

public interface AmountNode extends Node {
    Amount execute(DataContext context);
}
