package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.config.DataSourceConfig;
import net.schimweg.financeProcessor.model.DataProvider;

import java.util.HashMap;
import java.util.Map;

public class DataSourceManager {
    private final HashMap<String, DataProviderFactory> dataProviderFactories = new HashMap<>();

    public void addDataProviderType(String name, DataProviderFactory factory) {
        dataProviderFactories.put(name, factory);
    }

    public Map<String, DataProvider> initializeDataProviders(Map<String, DataSourceConfig> configs) throws PluginLoadException {
        HashMap<String, DataProvider> providers = new HashMap<>();

        for (Map.Entry<String, DataSourceConfig> entry : configs.entrySet()) {
            var factory = dataProviderFactories.get(entry.getValue().type);
            if (factory == null) {
                throw new PluginLoadException("Provider type " + entry.getValue().type + " not found!");
            }

            var result = factory.create(entry.getValue().config);
            providers.put(entry.getKey(), result);
        }


        return providers;
    }
}
