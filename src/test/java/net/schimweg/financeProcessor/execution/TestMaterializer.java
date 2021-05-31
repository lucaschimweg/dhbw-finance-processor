package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.model.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMaterializer {
    @Test
    void testMaterializer() {
        var transactions = List.of(
                new Transaction(0L, new Amount(2500, "USD"), "Test", 1, TransactionDirection.INCOMING),
                new Transaction(1L, new Amount(5000, "USD"), "Test", 1, TransactionDirection.INCOMING)
        );

        var resultData = new HashMap<String, FinanceObject>();
        resultData.put("null", null);
        resultData.put("amount", new Amount(200L, "EUR"));
        resultData.put("set", new ListTransactionSet(transactions));

        var results = new Result(resultData, 25);
        assertEquals(25, results.getExecutionTime());

        Materializer m = new Materializer();
        var matResults = assertDoesNotThrow( () -> m.materialize(results));

        assertTrue(matResults.results.containsKey("null"));
        assertTrue(matResults.results.containsKey("amount"));
        assertTrue(matResults.results.containsKey("set"));

        assertNull(matResults.results.get("null"));
        assertEquals(new Amount(200L, "EUR"), matResults.results.get("amount"));
        assertTrue(matResults.results.get("set") instanceof MaterializedTransactionSet);

        var matTrSet = (MaterializedTransactionSet) matResults.results.get("set");

        var transactionsSet = new HashSet<>(transactions);
        assertEquals(transactionsSet, matTrSet);
        assertTrue(transactionsSet.containsAll(matTrSet));
        assertTrue(matTrSet.containsAll(transactionsSet));
    }
}
