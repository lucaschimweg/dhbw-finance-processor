package net.schimweg.financeProcessor.plugin;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Type responsible for loading Plugins from a directory
 */
public class PluginLoader {

    /**
     * Loads a plugin from a single JAR-File. The JAR-File has to contain a resource called `main`, containing
     * only the name of the Plugin's main type, which implements the Plugin interface
     * @param jarFile The URL of the file to load
     * @return The LoadedPlugin created from the JAR-File
     * @throws PluginLoadException Thrown if anything goes wrong during the load process
     */
    public LoadedPlugin loadPlugin(URL jarFile) throws PluginLoadException {
        URLClassLoader loader = new URLClassLoader(new URL[] { jarFile });

        InputStream manifestStream = loader.getResourceAsStream("main");
        if (manifestStream == null) {
            throw new PluginLoadException("Manifest not found");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(manifestStream));
        String mainClassName;
        try {
            mainClassName = reader.readLine();
        } catch (IOException e) {
            throw new PluginLoadException("Could not load manifest", e);
        }

        Class<?> mainClass;
        try {
            mainClass = loader.loadClass(mainClassName);
        } catch (ClassNotFoundException e) {
            throw new PluginLoadException("Could not find main class", e);
        }

        Object mainObject;
        try {
            mainObject = mainClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new PluginLoadException("Could not initiate main class", e);
        }

        if (!(mainObject instanceof Plugin)) {
            throw new PluginLoadException("Main class is not an instance of Plugin");
        }

        return new LoadedPlugin(jarFile.getFile(), (Plugin) mainObject);
    }

    /**
     * Tries to load all .jar files in the specified dictionary as plugins. Subdirectories are ignored. Non-JAR files
     * are also ignored. Plugins that cause an exception when trying to be loaded are ignored.
     * @param directory The directory to load Plugins from
     * @return A list of successfully loaded plugins
     * @throws PluginLoadException Thrown if the specified File is not a directory or the access to it failed
     */
    public List<LoadedPlugin> loadPlugins(File directory) throws PluginLoadException {
        if (!directory.isDirectory()) {
            throw new PluginLoadException("Not a directory");
        }

        File[] files = directory.listFiles();

        if (files == null) {
            throw new PluginLoadException("Could not list plugins in directory");
        }

        List<LoadedPlugin> plugins = new ArrayList<>(files.length);

        for (File jar : files) {
            if (jar.isDirectory() || !jar.getName().endsWith(".jar")) {
                continue;
            }

            LoadedPlugin plugin;
            try {
                plugin = this.loadPlugin(jar.toURI().toURL());
            } catch (MalformedURLException | PluginLoadException e) {
                Exception ex = new PluginLoadException(String.format("Error while loading plugin %s", jar.getAbsolutePath()), e);
                ex.printStackTrace();
                continue;
            }

            plugins.add(plugin);
        }

        return plugins;
    }
}
