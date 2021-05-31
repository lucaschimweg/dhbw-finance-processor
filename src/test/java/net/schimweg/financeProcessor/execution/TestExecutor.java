package net.schimweg.financeProcessor.execution;

import net.schimweg.financeProcessor.Common;
import net.schimweg.financeProcessor.ast.AstRoot;
import net.schimweg.financeProcessor.ast.Node;
import net.schimweg.financeProcessor.model.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestExecutor {
    @Test
    void testExecutor() {
        var ctx = new DataContext(null);
        var called = new boolean[] {false, false, false};

        var calculations = new HashMap<String, Node>();
        calculations.put("null", context -> {
            assertEquals(context, ctx);
            called[0] = true;
            return null;
        });
        calculations.put("amount", context -> {
            assertEquals(context, ctx);
            called[1] = true;
            return new Amount(200L, "EUR");
        });
        calculations.put("set", context -> {
            assertEquals(context, ctx);
            called[2] = true;
            return new ListTransactionSet(List.of(Common.getEmptyTransaction())) {
                @Override
                public boolean next() {
                    fail();         // Should not be called by executor
                    return super.next();
                }

                @Override
                public Transaction current() {
                    fail();         // Should not be called by executor
                    return super.current();
                }
            };
        });

        AstRoot mockRoot = new AstRoot(calculations);

        Executor ex = new Executor(ctx);

        var results = assertDoesNotThrow(() -> ex.execute(mockRoot));

        assertTrue(called[0]);
        assertTrue(called[1]);
        assertTrue(called[2]);

        assertTrue(results.getResults().containsKey("null"));
        assertTrue(results.getResults().containsKey("amount"));
        assertTrue(results.getResults().containsKey("set"));

        assertNull(results.getResults().get("null"));
        assertEquals(new Amount(200L, "EUR"), results.getResults().get("amount"));
        assertTrue(results.getResults().get("set") instanceof TransactionSet);

        assertEquals(results.toString(), results.toString());
    }
}
