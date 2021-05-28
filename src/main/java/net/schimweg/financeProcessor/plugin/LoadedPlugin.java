package net.schimweg.financeProcessor.plugin;

import java.util.Objects;

/**
 * Represents a named plugin already loaded into memory
 */
public final class LoadedPlugin {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadedPlugin that = (LoadedPlugin) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
