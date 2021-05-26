package net.schimweg.financeProcessor;

import net.schimweg.financeProcessor.config.Config;
import net.schimweg.financeProcessor.config.ConfigLoader;
import net.schimweg.financeProcessor.execution.Executor;
import net.schimweg.financeProcessor.http.Server;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.plugin.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class Main {
    public static void main(String[] args) throws PluginLoadException, FileNotFoundException {
        Config config = new ConfigLoader().loadConfig(new File("config.yml"));

        PluginLoader loader = new PluginLoader();
        List<LoadedPlugin> plugins = loader.loadPlugins(new File("plugins"));

        PluginManager manager = new PluginManager();
        manager.initializePlugins(plugins);

        var dataProviders = manager.initializeDataProviders(config.dataSources);

        Executor executor = new Executor(new DataContext(dataProviders));

        try {
            Server s = new Server(config, manager, executor);
            s.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
