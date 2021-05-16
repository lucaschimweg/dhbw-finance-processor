package net.schimweg.financeProcessor.model;

public interface TransactionSet extends FinanceObject {
    Transaction current();
    boolean next();
}
