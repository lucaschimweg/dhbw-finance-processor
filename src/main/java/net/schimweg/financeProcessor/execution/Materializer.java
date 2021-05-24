package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.model.FinanceObject;
import net.schimweg.financeProcessor.model.MaterializedFinanceObject;
import net.schimweg.financeProcessor.model.MaterializedTransactionSet;
import net.schimweg.financeProcessor.model.TransactionSet;

import java.util.HashMap;
import java.util.Map;

public class Materializer {
    public MaterializedResult materialize(Result result) throws EvaluationException {
        long startTime = System.currentTimeMillis();
        HashMap<String, MaterializedFinanceObject> materializedObjects = new HashMap<>();

        for (Map.Entry<String, FinanceObject> entry : result.getResults().entrySet()) {
            if (entry.getValue() instanceof MaterializedFinanceObject) {
                materializedObjects.put(entry.getKey(), (MaterializedFinanceObject) entry.getValue());
            } else {
                materializedObjects.put(entry.getKey(), materializeObject(entry.getValue()));
            }
        }

        return new MaterializedResult(materializedObjects, result.getExecutionTime(), System.currentTimeMillis() - startTime);
    }


    private MaterializedFinanceObject materializeObject(FinanceObject object) throws EvaluationException {
        if (object instanceof TransactionSet) {
            return materializeTransactionSet((TransactionSet) object);
        }

        return null;
    }

    private MaterializedTransactionSet materializeTransactionSet(TransactionSet set) throws EvaluationException {
        MaterializedTransactionSet mat = new MaterializedTransactionSet();
        while (set.next()) {
            mat.add(set.current());
        }

        return mat;
    }
}
