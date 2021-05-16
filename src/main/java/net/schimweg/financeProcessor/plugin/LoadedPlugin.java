package net.schimweg.financeProcessor.plugin;

public class LoadedPlugin {
    private final String name;
    private final Plugin plugin;

    public LoadedPlugin(String name, Plugin plugin) {
        this.name = name;
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
