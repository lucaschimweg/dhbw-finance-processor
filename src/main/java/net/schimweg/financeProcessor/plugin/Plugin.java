package net.schimweg.financeProcessor.plugin;

/**
 * The main interface to be implemented by external plugins. It defines the entry points for plugins which is called
 * by the PluginManager
 * @see PluginManager
 */
public interface Plugin {
    /**
     * Initialized the Plugin and registers all types contained in it
     * @param pluginManager The PluginManager to use for registering types
     */
    void initialize(PluginManager pluginManager);
}
