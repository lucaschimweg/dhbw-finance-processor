package net.schimweg.financeProcessor.ast;

import java.util.Map;

/**
 * The root of an Abstract Syntax Tree
 */
public class AstRoot {
    /**
     * The calculations in the tree
     */
    private final Map<String, Node> calculations;

    /**
     * @param calculations The calculations contained in the tree
     */
    public AstRoot(Map<String, Node> calculations) {
        this.calculations = calculations;
    }

    /**
     * @return The individual calculation nodes in the tree, identified by their name
     */
    public Map<String, Node> getCalculations() {
        return calculations;
    }
}
