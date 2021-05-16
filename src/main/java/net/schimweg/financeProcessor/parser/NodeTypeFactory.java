package net.schimweg.financeProcessor.parser;

import net.schimweg.financeProcessor.ast.Node;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.function.Function;

public class NodeTypeFactory {
    public static class TypeEntry {
        private final Type configType;
        private final Function<Object, Node> factory;

        public TypeEntry(Type configType, Function<Object, Node> factory) {
            this.configType = configType;
            this.factory = factory;
        }

        public Type getConfigType() {
            return configType;
        }

        public Function<Object, Node> getFactory() {
            return factory;
        }
    }

    private final HashMap<String, TypeEntry> types;

    public NodeTypeFactory() {
        types = new HashMap<>();
    }

    public void addType(String name, Type type, Function<Object, Node> factory) {
        types.put(name.toLowerCase(), new TypeEntry(type, factory));
    }

    public TypeEntry getTypeInformation(String name) {
        return types.get(name.toLowerCase());
    }
}
