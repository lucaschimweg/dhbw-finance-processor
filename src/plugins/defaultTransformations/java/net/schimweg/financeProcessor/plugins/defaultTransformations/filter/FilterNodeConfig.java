package net.schimweg.financeProcessor.plugins.defaultTransformations.filter;

import net.schimweg.financeProcessor.ast.TransactionSetNode;

public class FilterNodeConfig {
    public TransactionSetNode source;
    public Condition[] conditions;
}
