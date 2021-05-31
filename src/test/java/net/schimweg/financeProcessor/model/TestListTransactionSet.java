package net.schimweg.financeProcessor.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestListTransactionSet {
    @Test
    public void testListTransactionSet() {
        Transaction tr1 = new Transaction(0L, new Amount(2500, "EUR"), "Test 1", 1, TransactionDirection.OUTGOING);
        Transaction tr2 = new Transaction(1L, new Amount(5000, "EUR"), "Test 2", 1, TransactionDirection.OUTGOING);
        Transaction tr3 = new Transaction(2L, new Amount(1000, "EUR"), "Test 3", 1, TransactionDirection.OUTGOING);
        var transactionsOriginal = new HashSet<>(List.of(tr1, tr2, tr3));

        var listTransactionSet = new ListTransactionSet(List.of(tr1, tr2, tr3));

        while (listTransactionSet.next()) {
            var tr = listTransactionSet.current();
            assertTrue(transactionsOriginal.contains(tr));
            transactionsOriginal.remove(tr);
        }
        assertEquals(0, transactionsOriginal.size());
    }

    @Test
    public void testEmptyListTransactionSet() {
        var listTransactionSet = new ListTransactionSet(List.of());
        assertFalse(listTransactionSet.next());
    }
}
