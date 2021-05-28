package net.schimweg.financeProcessor.model;

import java.util.Map;

/**
 * Represents a collection of different named data providers
 */
public class DataContext {
    private final Map<String, DataProvider> dataProviders;

    /**
     * Create a new DataContext
     * @param dataProvider The DataContext's providers
     */
    public DataContext(Map<String, DataProvider> dataProvider) {
        this.dataProviders = dataProvider;
    }

    /**
     * Finds a DataProvider
     * @param name The name of the data provider to find
     * @return The data provider if found by the name, otherwise null
     */
    public DataProvider getDataProvider(String name) {
        return dataProviders.get(name);
    }
}
