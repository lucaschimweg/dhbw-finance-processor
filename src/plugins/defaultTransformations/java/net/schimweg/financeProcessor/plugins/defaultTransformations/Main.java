package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.plugin.Plugin;
import net.schimweg.financeProcessor.plugin.PluginManager;

public class Main implements Plugin {

    @Override
    public void initialize(PluginManager manager) {
        manager.addNodeType("sum", SumNode.SumNodeConfig.class, o -> new SumNode((SumNode.SumNodeConfig) o));
        manager.addNodeType("union", UnionNode.UnionNodeConfig.class, o -> new UnionNode((UnionNode.UnionNodeConfig) o));
        manager.addNodeType("filter", FilterNode.FilterNodeConfig.class, o -> new FilterNode((FilterNode.FilterNodeConfig) o));
        manager.addNodeType("all_transactions", null, o -> new AllTransactionsNode());
        manager.addNodeType("count", CountNode.CountNodeConfig.class, o -> new CountNode((CountNode.CountNodeConfig) o));
    }
}
