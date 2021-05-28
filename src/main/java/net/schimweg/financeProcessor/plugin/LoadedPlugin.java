package net.schimweg.financeProcessor.plugin;

/**
 * Represents a named plugin already loaded into memory
 */
public class LoadedPlugin {
    private final String name;
    private final Plugin plugin;

    /**
     * Creates a new instance
     * @param name The name of the plugin, used to identify it
     * @param plugin The Plugin object used for interaction with the plugin
     */
    public LoadedPlugin(String name, Plugin plugin) {
        this.name = name;
        this.plugin = plugin;
    }

    /**
     * @return The name of the plugin, used to identify it
     */
    public String getName() {
        return name;
    }

    /**
     * @return The Plugin object used for interaction with the plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }
}
