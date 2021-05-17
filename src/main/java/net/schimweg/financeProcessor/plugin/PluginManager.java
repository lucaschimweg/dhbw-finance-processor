package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.ast.Node;
import net.schimweg.financeProcessor.parser.NodeTypeFactory;
import net.schimweg.financeProcessor.parser.Parser;

import java.lang.reflect.Type;
import java.util.function.Function;

public class PluginManager {
    private final NodeTypeFactory typeFactory = new NodeTypeFactory();

    public PluginManager() {

    }

    public NodeTypeFactory getTypeFactory() {
        return typeFactory;
    }

    public void addNodeType(String name, Type type, Function<Object, Node> factory) {
        System.out.printf("Adding type %s\n", name);
        typeFactory.addType(name, type, factory);
    }

    public void addParserType(String contentType, Function<NodeTypeFactory, Parser> factory) {
        System.out.printf("Adding parser for type %s", contentType);
    }


}