package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.model.DataProvider;

import java.util.Map;

/**
 * Factory to create a new DataProvider from a configuration
 */
public interface DataProviderFactory {
    /**
     * Creates a new DataProvider from the given configuration
     * @param config The specified configuration
     * @return The initialized DataProvider
     * @throws PluginLoadException Thrown when anything goes wrong during the initialization
     */
    DataProvider create(Map<String, String> config) throws PluginLoadException;
}
