package net.schimweg.financeProcessor.model;

import java.util.List;

public class ListTransactionSet implements TransactionSet {
    private final List<Transaction> transactions;
    private int index = -1;

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
