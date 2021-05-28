package net.schimweg.financeProcessor.config;

import java.util.Map;

/**
 * The configuration for a single data source, which represents any kind of origin for financial data. DataProviders
 * are objects used to parse and source these sources.
 * @see net.schimweg.financeProcessor.model.DataProvider
 */
public class DataSourceConfig {
    /**
     * The DataProvider's type name that would be used for this data source
     */
    public String type;

    /**
     * The type-dependent configuration of this data source
     */
    public Map<String, String> config;
}
