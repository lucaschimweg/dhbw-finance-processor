package net.schimweg.financeProcessor.model;

import java.util.List;

/**
 * Implements a TransactionSet based on a simple list
 */
public class ListTransactionSet implements TransactionSet {
    private final List<Transaction> transactions;
    private int index = -1;

    /**
     * Creates a new ListTransactionSet from a given list
     * @param transactions The list to use for this TransactionSet
     */
    public ListTransactionSet(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Transaction current() {
        return transactions.get(index);
    }

    @Override
    public boolean next() {
        ++index;
        return index < transactions.size();
    }
}
