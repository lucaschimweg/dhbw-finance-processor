package net.schimweg.financeProcessor.ast;

import java.util.Map;

public class AstRoot {
    private final String dataSourceName;
    private final Map<String, Node> calculations;

    public AstRoot(String dataSourceName, Map<String, Node> calculations) {
        this.dataSourceName = dataSourceName;
        this.calculations = calculations;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public Map<String, Node> getCalculations() {
        return calculations;
    }
}
