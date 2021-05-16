package net.schimweg.financeProcessor.plugin;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginLoader {

    public Plugin loadPlugin(URL jarFile) throws PluginLoadException {
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

        return (Plugin) mainObject;
    }

    public List<Plugin> loadPlugins(File directory) throws PluginLoadException {
        if (!directory.isDirectory()) {
            throw new PluginLoadException("Not a directory");
        }

        File[] files = directory.listFiles();

        if (files == null) {
            throw new PluginLoadException("Could not list plugins in directory");
        }

        List<Plugin> plugins = new ArrayList<>(files.length);

        for (File jar : files) {
            if (jar.isDirectory() || !jar.getName().endsWith(".jar")) {
                continue;
            }

            Plugin plugin;
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
