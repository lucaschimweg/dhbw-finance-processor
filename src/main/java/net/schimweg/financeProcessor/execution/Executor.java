package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.ast.AstRoot;
import net.schimweg.financeProcessor.model.DataContext;
import net.schimweg.financeProcessor.model.FinanceObject;

import java.util.HashMap;

public class Executor {

    private final HashMap<String, DataContext> rootContexts = new HashMap<>();

    public void addContext(String name, DataContext context) {
        rootContexts.put(name, context);
    }

    public Result execute(AstRoot root) {
        var startTime = System.currentTimeMillis();
        var results = new HashMap<String, FinanceObject>();

        var dataContext = rootContexts.get(root.getDataSourceName());
        if (dataContext == null) {
            throw new RuntimeException("Unknown data source " + root.getDataSourceName());
        }


        for (var entry : root.getCalculations().entrySet()) {
            results.put(entry.getKey(), entry.getValue().execute(dataContext));
        }

        return new Result(results, System.currentTimeMillis() - startTime);

    }
}
