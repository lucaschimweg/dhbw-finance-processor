package net.schimweg.financeProcessor.config;

import java.util.Map;

public class Config {
    public int port = 8080;
    public Map<String, DataSourceConfig> dataSources;
}
