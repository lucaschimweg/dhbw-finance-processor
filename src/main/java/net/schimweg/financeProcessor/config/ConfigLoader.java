package net.schimweg.financeProcessor.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class ConfigLoader {
    public Config loadConfig(File file) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(Config.class));
        return yaml.load(new FileReader(file));
    }
}
