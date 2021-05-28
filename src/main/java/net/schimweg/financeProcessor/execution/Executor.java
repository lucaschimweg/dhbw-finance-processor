package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.ast.AstRoot;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.FinanceObject;

import java.util.HashMap;

/**
 * A class used to execute AstRoot objects
 */
public class Executor {

    private final DataContext dataContext;

    /**
     * Create a new Executor with a DataContext
     * @param dataContext The data context containing all data sources used for the execution
     */
    public Executor(DataContext dataContext) {
        this.dataContext = dataContext;
    }

    /**
     * Executes an AstRoot to get a Result
     * @param root The AstRoot to execute
     * @return The Result from the execution
     * @throws EvaluationException Thrown if any calculation itself throws an EvaluationException
     */
    public Result execute(AstRoot root) throws EvaluationException {
        var startTime = System.currentTimeMillis();
        var results = new HashMap<String, FinanceObject>();

        for (var entry : root.getCalculations().entrySet()) {
            results.put(entry.getKey(), entry.getValue().execute(dataContext));
        }

        return new Result(results, System.currentTimeMillis() - startTime);
    }

}
