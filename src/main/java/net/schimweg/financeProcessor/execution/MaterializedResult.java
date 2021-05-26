package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.model.MaterializedFinanceObject;

import java.util.Map;

public class MaterializedResult {
    public final Map<String, MaterializedFinanceObject> results;
    public final long executionTime;
    public final long materializationTime;

    public MaterializedResult(Map<String, MaterializedFinanceObject> results, long executionTime, long materializationTime) {
        this.results = results;
        this.executionTime = executionTime;
        this.materializationTime = materializationTime;
    }

    @Override
    public String toString() {
        return "MaterializedResult{" +
                "executionTime=" + executionTime +
                ", results=" + results +
                '}';
    }
}
