package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.ast.Node;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Manages loaded Node types
 */
public class NodeTypeFactory {
    /**
     * A type entry, containing the configuration type for the node type, and a factory function used to create it
     */
    public static class TypeEntry {
        private final Type configType;
        private final Function<Object, Node> factory;

        /**
         * Creates a new TypeEntry object
         * @param configType The configuration type required for this node type
         * @param factory The factory to create the node type from the configuration type
         */
        public TypeEntry(Type configType, Function<Object, Node> factory) {
            this.configType = configType;
            this.factory = factory;
        }

        /**
         * @return The configuration type required for this node type
         */
        public Type getConfigType() {
            return configType;
        }

        /**
         * @return The factory to create the node type from the configuration type
         */
        public Function<Object, Node> getFactory() {
            return factory;
        }
    }

    private final HashMap<String, TypeEntry> types;

    /**
     * Creates a new empty instance
     */
    public NodeTypeFactory() {
        types = new HashMap<>();
    }

    /**
     * Adds a new type identified by a name
     * @param name The name for this type
     * @param type The configuration type required for this node type
     * @param factory The factory to create the node type from the configuration type
     */
    public void addType(String name, Type type, Function<Object, Node> factory) {
        types.put(name.toLowerCase(), new TypeEntry(type, factory));
    }

    /**
     * Finds a node type's information by the specified name
     * @param name The type's name
     * @return The TypeEntry object for this type if it is found, null otherwise.
     */
    public TypeEntry getTypeInformation(String name) {
        return types.get(name.toLowerCase());
    }
}
