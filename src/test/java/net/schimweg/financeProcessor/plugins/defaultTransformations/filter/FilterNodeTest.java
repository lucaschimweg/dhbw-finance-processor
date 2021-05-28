package net.schimweg.financeProcessor.plugins.defaultTransformations.filter;

import net.schimweg.financeProcessor.Common;
import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.Amount;
import net.schimweg.financeProcessor.model.Transaction;
import net.schimweg.financeProcessor.model.TransactionDirection;
import net.schimweg.financeProcessor.plugins.defaultTransformations.AllTransactionsNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilterNodeTest {
    @Test
    void testFilterNode() throws EvaluationException {
        var data = Common.getMockDataContext(
                new Transaction(id, new Amount(2500, "EUR"), "Test 1", 1, TransactionDirection.OUTGOING),
                new Transaction(id, new Amount(3000, "EUR"), "Test 2", 1, TransactionDirection.OUTGOING),
                new Transaction(id, new Amount(3500, "EUR"), "Test 3", 1, TransactionDirection.INCOMING)
        );

        var config = new FilterNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        Condition c = new Condition();
        c.type = "equals";
        c.field = "amount_value";
        c.numValue = 3000L;
        config.conditions = new Condition[] { c };
        var node = new FilterNode(config);

        var result = node.execute(data);

        assertTrue(result.next());
        assertEquals(3000L, result.current().getAmount().getValue());
        assertEquals("Test 2", result.current().getSubject());

        assertFalse(result.next());
    }

    @Test
    void testFilterNodeErrors() {
        var data = Common.getMockDataContext(
                new Transaction(id, new Amount(2500, "EUR"), "Test 1", 1, TransactionDirection.OUTGOING)
        );

        var config = new FilterNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        Condition c = new Condition();
        c.type = "equals";
        c.field = "amount_value";
        config.conditions = new Condition[] { c };

        var node = new FilterNode(config);
        assertThrows(EvaluationException.class, () -> node.execute(data));
    }
}
