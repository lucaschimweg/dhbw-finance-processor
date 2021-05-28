package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.ast.Node;
import net.schimweg.financeProcessor.config.DataSourceConfig;
import net.schimweg.financeProcessor.model.DataProvider;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The primary interaction point for Plugins with the platform
 */
public class PluginManager {
    private final NodeTypeFactory typeFactory = new NodeTypeFactory();
    private final HashMap<String, Function<NodeTypeFactory, Parser>> parserFactories = new HashMap<>();
    private final HashMap<String, Supplier<Encoder>> encoderFactories = new HashMap<>();
    private final DataProviderManager dataSourceManager = new DataProviderManager();

    /**
     * @return The type factory containing all registered types
     */
    public NodeTypeFactory getTypeFactory() {
        return typeFactory;
    }

    /**
     * Tries to find a parser for the given MIME-Type
     * @param contentType The MIME-Type to find a parser for
     * @return The parser for the given MIME-Type, or null if no parser was found
     */
    public Parser getParserFor(String contentType) {
        Function<NodeTypeFactory, Parser> factory = parserFactories.get(contentType);
        if (factory == null) {
            return null;
        }
        return factory.apply(getTypeFactory());
    }

    /**
     * Tries to find an encoder for the given MIME-Type
     * @param contentType The MIME-Type to find an encoder for
     * @return The encoder for the given MIME-Type, or null if no encoder was found
     */
    public Encoder getEncoderFor(String contentType) {
        Supplier<Encoder> factory = encoderFactories.get(contentType);
        if (factory == null) {
            return null;
        }
        return factory.get();
    }

    /**
     * @return Returns the default encoder to use if no encoder was specified in a request
     */
    public Encoder getDefaultEncoder() {
        if (encoderFactories.size() == 0) {
            return null;
        }
        return encoderFactories.values().stream().findFirst().get().get();
    }

    /**
     * To be called by plugins. Adds a new node type to the platform
     * @param name The identifier of the new node type
     * @param type The configuration type for the new node type
     * @param factory A factory function creating the new node type from the given configuration type
     */
    public void addNodeType(String name, Type type, Function<Object, Node> factory) {
        System.out.printf("Adding type %s\n", name);
        typeFactory.addType(name, type, factory);
    }


    /**
     * To be called by plugins. Adds a new parser type listening on a specific MIME-Type
     * @param contentType The MIME-Type supported by the parser
     * @param factory The factory function used to create a new instance of the parser from a NodeTypeFactory
     */
    public void addParserType(String contentType, Function<NodeTypeFactory, Parser> factory) {
        System.out.printf("Adding parser for type %s\n", contentType);
        parserFactories.put(contentType, factory);
    }

    /**
     * To be called by plugins. Adds a new encoder type listening on a specific MIME-Type
     * @param contentType The MIME-Type supported by the encoder
     * @param factory The factory function used to create a new instance of the encoder
     */
    public void addEncoderType(String contentType, Supplier<Encoder> factory){
        System.out.printf("Adding encoder for type %s\n", contentType);
        encoderFactories.put(contentType, factory);
    }

    /**
     * To be called by plugins. Adds a data provider type
     * @param name The name identifying the provider type
     * @param factory The factory to create a provider instance
     */
    public void addDataProviderType(String name, DataProviderFactory factory) {
        dataSourceManager.addDataProviderType(name, factory);
    }

    /**
     * Initializes DataProviders from the given configuration
     * @param config Named DataSource configurations
     * @return Initialized DataProviders identified by their names
     * @throws PluginLoadException Thrown when an error occurs during the initialization
     */
    public Map<String, DataProvider> initializeDataProviders(Map<String, DataSourceConfig> config) throws PluginLoadException {
        return dataSourceManager.initializeDataProviders(config);
    }

    /**
     * Initializes the given List of LoadedPlugins with this PluginManager
     * @param plugins The List of Plugins to initialize
     */
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
