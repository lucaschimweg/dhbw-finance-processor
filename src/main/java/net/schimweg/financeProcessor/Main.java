package net.schimweg.financeProcessor;

import net.schimweg.financeProcessor.http.Server;
import net.schimweg.financeProcessor.plugin.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws PluginLoadException {
        PluginLoader loader = new PluginLoader();
        List<LoadedPlugin> plugins = loader.loadPlugins(new File("plugins"));

        PluginManager manager = new PluginManager();

        for (LoadedPlugin p : plugins) {
            System.out.printf("Initializing plugin %s\n", p.getName());
            try {
                p.getPlugin().initialize(manager);
            } catch (RuntimeException e) {
                PluginLoadException pl = new PluginLoadException(String.format("Error initializing plugin %s", p.getName()), e);
                pl.printStackTrace();
            }
        }

        try {
            Server s = new Server(manager);
            s.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
