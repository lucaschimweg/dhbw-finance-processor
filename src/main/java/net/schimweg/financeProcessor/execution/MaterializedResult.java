package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.model.MaterializedFinanceObject;

import java.util.Map;

public class MaterializedResult {
    private final Map<String, MaterializedFinanceObject> results;
    private final long executionTime;

    public MaterializedResult(Map<String, MaterializedFinanceObject> results, long executionTime) {
        this.results = results;
        this.executionTime = executionTime;
    }

    public Map<String, MaterializedFinanceObject> getResults() {
        return results;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public String toString() {
        return "MaterializedResult{" +
                "executionTime=" + executionTime +
                ", results=" + results +
                '}';
    }
}
