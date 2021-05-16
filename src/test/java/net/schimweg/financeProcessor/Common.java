package net.schimweg.financeProcessor;

import net.schimweg.financeProcessor.model.*;

import java.util.ArrayList;

public class Common {
    public static DataContext getDummyDataContext(int nTransactions) {
        var transaction = new Transaction(new Amount(1, Currency.EUR), "subject", 0, TransactionDirection.INCOMING);
        var transactionList = new ArrayList<Transaction>(nTransactions);

        for (int i = 0; i < nTransactions; ++i) {
           transactionList.add(transaction);
        }

        return new DataContext(() -> new ListTransactionSet(transactionList));
    }

}
