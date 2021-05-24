package net.schimweg.financeProcessor.plugins.defaultTransformations.filter;

import net.schimweg.financeProcessor.model.TransactionDirection;

public class Condition {
    public String type;
    public String field;
    public Long numValue;
    public String stringValue;
    public TransactionDirection directionValue;
}
