package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.model.FinanceObject;
import net.schimweg.financeProcessor.model.MaterializedFinanceObject;
import net.schimweg.financeProcessor.model.MaterializedTransactionSet;
import net.schimweg.financeProcessor.model.TransactionSet;

import java.util.HashMap;
import java.util.Map;

public class Materializer {
    public Materializer() {

    }

    public MaterializedResult materialize(Result result) {
        HashMap<String, MaterializedFinanceObject> materializedObjects = new HashMap<>();

        for (Map.Entry<String, FinanceObject> entry : result.getResults().entrySet()) {
            if (entry.getValue() instanceof MaterializedFinanceObject) {
                materializedObjects.put(entry.getKey(), (MaterializedFinanceObject) entry.getValue());
            } else {
                materializedObjects.put(entry.getKey(), materializeObject(entry.getValue()));
            }
        }

        return new MaterializedResult(materializedObjects, result.getExecutionTime());
    }


    private MaterializedFinanceObject materializeObject(FinanceObject object) {
        if (object instanceof TransactionSet) {
            return materializeTransactionSet((TransactionSet) object);
        }

        return null;
    }

    private MaterializedTransactionSet materializeTransactionSet(TransactionSet set) {
        MaterializedTransactionSet mat = new MaterializedTransactionSet();
        while (set.next()) {
            mat.add(set.current());
        }

        return mat;
    }
}
