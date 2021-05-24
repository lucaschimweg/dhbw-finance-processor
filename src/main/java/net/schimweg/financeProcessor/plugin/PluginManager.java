package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.ast.Node;
import net.schimweg.financeProcessor.parser.NodeTypeFactory;
import net.schimweg.financeProcessor.parser.Parser;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class PluginManager {
    private final NodeTypeFactory typeFactory = new NodeTypeFactory();
    private final HashMap<String, Function<NodeTypeFactory, Parser>> parserFactories = new HashMap<>();
    private final HashMap<String, Supplier<Encoder>> encoderFactories = new HashMap<>();

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


}
