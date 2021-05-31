package net.schimweg.financeProcessor;

import net.schimweg.financeProcessor.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Common {
    public static final String MockDataContextName = "data";

    public static DataContext getMockDataContext(int nTransactions) {
        var transactionList = new ArrayList<Transaction>(nTransactions);

        for (long i = 0; i < nTransactions; ++i) {
           transactionList.add(new Transaction(i, new Amount(1, "EUR"), "subject", 0, TransactionDirection.INCOMING));
        }

        HashMap<String, DataProvider> dataProviders = new HashMap<>();
        dataProviders.put(MockDataContextName, () -> new ListTransactionSet(transactionList));

        return new DataContext(dataProviders);
    }

    public static DataContext getMockDataContext(Transaction ...transactions) {
        var transactionList = Arrays.asList(transactions);

        HashMap<String, DataProvider> dataProviders = new HashMap<>();
        dataProviders.put(MockDataContextName, () -> new ListTransactionSet(transactionList));

        return new DataContext(dataProviders);
    }
}
