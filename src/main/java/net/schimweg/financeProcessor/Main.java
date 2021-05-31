package net.schimweg.financeProcessor;

import net.schimweg.financeProcessor.config.Config;
import net.schimweg.financeProcessor.config.ConfigLoader;
import net.schimweg.financeProcessor.execution.Executor;
import net.schimweg.financeProcessor.http.Server;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.plugin.LoadedPlugin;
import net.schimweg.financeProcessor.plugin.PluginLoader;
import net.schimweg.financeProcessor.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The application's entry point
 */
public class Main {
    /**
     * The applications main function
     * @param args Passed Command-Line Arguments
     */
    public static void main(String[] args) {
        try {
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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
