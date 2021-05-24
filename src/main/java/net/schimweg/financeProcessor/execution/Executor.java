package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.ast.AstRoot;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.FinanceObject;
import net.schimweg.financeProcessor.model.MaterializedTransactionSet;

import java.util.HashMap;

public class Executor {

    private final DataContext dataContext;

    public Executor(DataContext dataContext) {
        this.dataContext = dataContext;
    }

    public Result execute(AstRoot root) {
        var startTime = System.currentTimeMillis();
        var results = new HashMap<String, FinanceObject>();

        for (var entry : root.getCalculations().entrySet()) {
            results.put(entry.getKey(), entry.getValue().execute(dataContext));
        }

        return new Result(results, System.currentTimeMillis() - startTime);
    }

}
