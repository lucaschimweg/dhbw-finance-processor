package net.schimweg.financeProcessor.model;

import java.util.HashMap;
import java.util.Map;

public class DataContext {
    private final Map<String, DataProvider> dataProviders;

    public DataContext(Map<String, DataProvider> dataProvider) {
        this.dataProviders = dataProvider;
    }

    public DataProvider getDataProvider(String name) {
        return dataProviders.get(name);
    }
}
