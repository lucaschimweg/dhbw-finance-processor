package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.model.FinanceObject;

import java.util.Map;

/**
 * The results of an execution
 */
public class Result {
    private final Map<String, FinanceObject> results;
    private final long executionTime;

    /**
     * Creates a new Result object from a given set of results and the execution time
     * @param results The results from the execution
     * @param executionTime The time the execution took (in milliseconds)
     */
    public Result(Map<String, FinanceObject> results, long executionTime) {
        this.results = results;
        this.executionTime = executionTime;
    }

    /**
     * @return The execution's results, by name of calculation
     */
    public Map<String, FinanceObject> getResults() {
        return results;
    }

    /**
     * @return The time the execution took
     */
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
