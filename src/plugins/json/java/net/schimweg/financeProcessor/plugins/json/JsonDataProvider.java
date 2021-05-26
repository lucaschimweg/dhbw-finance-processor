package net.schimweg.financeProcessor.plugins.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.DataProvider;
import net.schimweg.financeProcessor.model.ListTransactionSet;
import net.schimweg.financeProcessor.model.Transaction;
import net.schimweg.financeProcessor.model.TransactionSet;
import net.schimweg.financeProcessor.plugin.PluginLoadException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class JsonDataProvider implements DataProvider {

    private List<Transaction> transactionList;
    private final File jsonFile;

    @Override
    public TransactionSet getAllTransactions() throws EvaluationException {
        if (transactionList == null) {
            loadList();
        }

        return new ListTransactionSet(transactionList);
    }

    private void loadList() throws EvaluationException {
        try {
            Gson g = new Gson();
            transactionList = g.fromJson(new FileReader(jsonFile), new TypeToken<List<Transaction>>(){ }.getType());
        } catch (FileNotFoundException e) {
            throw new EvaluationException("Error reading file", e);
        }
    }

    public JsonDataProvider(Map<String, String> config) throws PluginLoadException {
        String fileName = config.get("filename");
        if (fileName == null) {
            throw new PluginLoadException("Filename not specified");
        }

        jsonFile = new File(fileName);
        if (!jsonFile.exists()) {
            throw new PluginLoadException("Specified file does not exist");
        }
    }
}
