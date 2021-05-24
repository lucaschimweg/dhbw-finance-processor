package net.schimweg.financeProcessor;

import net.schimweg.financeProcessor.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Common {
    public static final String DefaultDataContextName = "data";

    public static DataContext getDummyDataContext(int nTransactions) {
        var transaction = new Transaction(new Amount(1, Currency.EUR), "subject", 0, TransactionDirection.INCOMING);
        var transactionList = new ArrayList<Transaction>(nTransactions);

        for (int i = 0; i < nTransactions; ++i) {
           transactionList.add(transaction);
        }

        HashMap<String, DataProvider> dataProviders = new HashMap<>();
        dataProviders.put(DefaultDataContextName, () -> new ListTransactionSet(transactionList));

        return new DataContext(dataProviders);
    }

}
