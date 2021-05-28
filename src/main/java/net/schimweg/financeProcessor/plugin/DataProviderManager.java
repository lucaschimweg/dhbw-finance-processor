package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.config.DataSourceConfig;
import net.schimweg.financeProcessor.model.DataProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages available DataProvider factories
 */
public class DataProviderManager {
    private final HashMap<String, DataProviderFactory> dataProviderFactories = new HashMap<>();

    /**
     * Adds a new DataProvider type
     * @param name The identifier of the type
     * @param factory The factory used to initialize the type with a config
     */
    public void addDataProviderType(String name, DataProviderFactory factory) {
        dataProviderFactories.put(name, factory);
    }

    /**
     * Initializes all DataProviders specified in the given configuration
     * @param configs The configuration, containing the configuration for data sources, identified by their name
     * @return The initialized DataProviders, identified by their DataSource's name from the config
     * @throws PluginLoadException Thrown when any DataProviderFactory throws an exception or a provider is not found
     */
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
