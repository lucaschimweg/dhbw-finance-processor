package net.schimweg.financeProcessor.model;

import java.util.HashMap;

public class DataContext {
    private final HashMap<String, DataProvider> dataProviders;

    public DataContext(HashMap<String, DataProvider> dataProvider) {
        this.dataProviders = dataProvider;
    }

    public DataProvider getDataProvider(String name) {
        return dataProviders.get(name);
    }
}
