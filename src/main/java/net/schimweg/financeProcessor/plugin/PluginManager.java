package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.ast.Node;
import net.schimweg.financeProcessor.config.DataSourceConfig;
import net.schimweg.financeProcessor.model.DataProvider;
import net.schimweg.financeProcessor.parser.NodeTypeFactory;
import net.schimweg.financeProcessor.parser.Parser;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class PluginManager {
    private final NodeTypeFactory typeFactory = new NodeTypeFactory();
    private final HashMap<String, Function<NodeTypeFactory, Parser>> parserFactories = new HashMap<>();
    private final HashMap<String, Supplier<Encoder>> encoderFactories = new HashMap<>();
    private final DataSourceManager dataSourceManager = new DataSourceManager();

    public PluginManager() {

    }

    public NodeTypeFactory getTypeFactory() {
        return typeFactory;
    }

    public Parser getParserFor(String contentType) {
        Function<NodeTypeFactory, Parser> factory = parserFactories.get(contentType);
        if (factory == null) {
            return null;
        }
        return factory.apply(getTypeFactory());
    }

    public Encoder getEncoderFor(String contentType) {
        Supplier<Encoder> factory = encoderFactories.get(contentType);
        if (factory == null) {
            return null;
        }
        return factory.get();
    }

    public Encoder getDefaultEncoder() {
        if (encoderFactories.size() == 0) {
            return null;
        }
        return encoderFactories.values().stream().findFirst().get().get();
    }

    public void addNodeType(String name, Type type, Function<Object, Node> factory) {
        System.out.printf("Adding type %s\n", name);
        typeFactory.addType(name, type, factory);
    }

    public void addParserType(String contentType, Function<NodeTypeFactory, Parser> factory) {
        System.out.printf("Adding parser for type %s\n", contentType);
        parserFactories.put(contentType, factory);
    }

    public void addEncoderType(String contentType, Supplier<Encoder> factory){
        System.out.printf("Adding encoder for type %s\n", contentType);
        encoderFactories.put(contentType, factory);
    }

    public void addDataProviderType(String name, DataProviderFactory factory) {
        dataSourceManager.addDataProviderType(name, factory);
    }

    public Map<String, DataProvider> initializeDataProviders(Map<String, DataSourceConfig> config) throws PluginLoadException {
        return dataSourceManager.initializeDataProviders(config);
    }

    public void initializePlugins(List<LoadedPlugin> plugins) {
        for (LoadedPlugin p : plugins) {
            System.out.printf("Initializing plugin %s\n", p.getName());
            try {
                p.getPlugin().initialize(this);
            } catch (RuntimeException e) {
                PluginLoadException pl = new PluginLoadException(String.format("Error initializing plugin %s", p.getName()), e);
                pl.printStackTrace();
            }
        }
    }
}
