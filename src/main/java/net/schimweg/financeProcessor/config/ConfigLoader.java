package net.schimweg.financeProcessor.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Loads and parses a configuration from a file
 */
public class ConfigLoader {
    /**
     * @param file The file to load the config from
     * @return The loaded Config object
     * @throws FileNotFoundException Thrown when the file does not exist or cannot be loaded
     */
    public Config loadConfig(File file) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(Config.class));
        return yaml.load(new FileReader(file));
    }
}
