package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.model.MaterializedFinanceObject;

import java.util.Map;

/**
 * A MaterializedResult is a Result that has all containing objects materialized = loaded into memory.
 * A Result object can contain TransactionSets, which are iterators to not have the full results in memory.
 * For serialization of the results, they will be loaded into memory before, this step is called materialization and
 * results in a MaterializedResult.
 */
public class MaterializedResult {
    /**
     * The results, accessible by name of calculation
     */
    public final Map<String, MaterializedFinanceObject> results;
    /**
     * The time it took to execute all calculations (in milliseconds)
     */
    public final long executionTime;
    /**
     * The time it took to materialize all results (in milliseconds)
     */
    public final long materializationTime;

    /**
     * Create a new MaterializedResult from the required parameters
     * @param results The materialized results themselves
     * @param executionTime The time it took to execute all calculations (in milliseconds)
     * @param materializationTime The time it took to materialize all calculations (in milliseconds)
     */
    public MaterializedResult(Map<String, MaterializedFinanceObject> results, long executionTime, long materializationTime) {
        this.results = results;
        this.executionTime = executionTime;
        this.materializationTime = materializationTime;
    }
}
