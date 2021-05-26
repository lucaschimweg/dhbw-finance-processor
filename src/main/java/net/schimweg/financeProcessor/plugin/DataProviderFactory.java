package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.model.DataProvider;

import java.util.Map;

public interface DataProviderFactory {
    DataProvider create(Map<String, String> config) throws PluginLoadException;
}
