package net.schimweg.financeProcessor.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransaction {
    @Test
    void testTransactionGetters() {
        Transaction tr1 = new Transaction(0L, new Amount(2500, "EUR"), "Test 1", 1, TransactionDirection.OUTGOING);

        assertEquals(0L, tr1.getId());
        assertEquals("Test 1", tr1.getSubject());
        assertEquals(1, tr1.getCostCenter());
        assertEquals(TransactionDirection.OUTGOING, tr1.getDirection());
    }

    @Test
    void testTransactionEquality() {
        Transaction tr1 = new Transaction(0L, new Amount(2500, "EUR"), "Test 1", 1, TransactionDirection.OUTGOING);
        Transaction tr2 = new Transaction(0L, new Amount(5000, "EUR"), "Test 2", 1, TransactionDirection.OUTGOING);
        Transaction tr3 = new Transaction(1L, new Amount(5000, "EUR"), "Test 2", 1, TransactionDirection.OUTGOING);

        assertEquals(tr1, tr2);     // Same ID
        assertNotEquals(tr1, tr3);
        assertNotEquals(tr2, tr3);  // Different ID

        assertEquals(tr1.hashCode(), tr2.hashCode());
        assertNotEquals(tr1.hashCode(), tr3.hashCode());
        assertNotEquals(tr2.hashCode(), tr3.hashCode());

        assertEquals(tr1, tr1);

        assertNotEquals(tr1, new Object());
        assertNotEquals(tr1, null);

        assertEquals(tr1.toString(), tr1.toString());
        assertNotEquals(tr1.toString(), tr2.toString());
        assertNotEquals(tr1.toString(), tr3.toString());
    }
}
