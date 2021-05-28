package net.schimweg.financeProcessor.config;

import java.util.Map;

/**
 * The configuration's type
 */
public class Config {
    /**
     * What port to run the HTTP server on
     */
    public int port = 8080;

    /**
     * The configured data sources
     */
    public Map<String, DataSourceConfig> dataSources;
}
