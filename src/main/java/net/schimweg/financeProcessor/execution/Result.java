package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.model.FinanceObject;

import java.util.Map;

public class Result {
    private final Map<String, FinanceObject> results;
    private final long executionTime;

    public Result(Map<String, FinanceObject> results, long executionTime) {
        this.results = results;
        this.executionTime = executionTime;
    }

    public Map<String, FinanceObject> getResults() {
        return results;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public String toString() {
        return "Result{" +
                "executionTime=" + executionTime +
                ", results=" + results +
                '}';
    }
}
