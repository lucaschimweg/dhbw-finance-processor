package net.schimweg.financeProcessor.ast;

import java.util.Map;

public class AstRoot {
    private final Map<String, Node> calculations;

    public AstRoot(Map<String, Node> calculations) {
        this.calculations = calculations;
    }

    public Map<String, Node> getCalculations() {
        return calculations;
    }
}
