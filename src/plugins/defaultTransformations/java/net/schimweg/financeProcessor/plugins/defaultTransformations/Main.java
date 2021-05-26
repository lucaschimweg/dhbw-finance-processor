package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.plugin.Plugin;
import net.schimweg.financeProcessor.plugin.PluginManager;
import net.schimweg.financeProcessor.plugins.defaultTransformations.filter.FilterNode;
import net.schimweg.financeProcessor.plugins.defaultTransformations.filter.FilterNodeConfig;

public class Main implements Plugin {

    @Override
    public void initialize(PluginManager manager) {
        manager.addNodeType("sum", SumNode.SumNodeConfig.class, o -> new SumNode((SumNode.SumNodeConfig) o));
        manager.addNodeType("union", UnionNode.UnionNodeConfig.class, o -> new UnionNode((UnionNode.UnionNodeConfig) o));
        manager.addNodeType("filter", FilterNodeConfig.class, o -> new FilterNode((FilterNodeConfig) o));
        manager.addNodeType("all_transactions", AllTransactionsNode.AllTransactionsNodeConfig.class, o -> new AllTransactionsNode((AllTransactionsNode.AllTransactionsNodeConfig) o));
        manager.addNodeType("count", CountNode.CountNodeConfig.class, o -> new CountNode((CountNode.CountNodeConfig) o));
        manager.addNodeType("max", MaxNode.MaxNodeConfig.class, o -> new MaxNode((MaxNode.MaxNodeConfig) o));
    }
}
