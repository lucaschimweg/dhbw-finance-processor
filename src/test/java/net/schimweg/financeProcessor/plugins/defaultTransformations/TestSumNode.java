package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.Common;
import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.Amount;
import net.schimweg.financeProcessor.model.Currency;
import net.schimweg.financeProcessor.model.Transaction;
import net.schimweg.financeProcessor.model.TransactionDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSumNode {
    @Test
    void testName() {
        var config = new SumNode.SumNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        var node = new SumNode(config);

        assertEquals("SUM", node.name());
    }

    @Test
    void testSum0() throws EvaluationException {
        var data = Common.getMockDataContext(0);

        var config = new SumNode.SumNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        var node = new SumNode(config);

        var result = node.execute(data);

        assertEquals(0, result.getValue());
        assertEquals(result.getCurrency(), Currency.NONE);
    }

    @Test
    void testSum1() throws EvaluationException {
        var data = Common.getMockDataContext(
                new Transaction(new Amount(2500, Currency.EUR), "Test", 1, TransactionDirection.INCOMING)
        );

        var config = new SumNode.SumNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        var node = new SumNode(config);

        var result = node.execute(data);

        assertEquals(2500, result.getValue());
        assertEquals(result.getCurrency(), Currency.EUR);
    }

    @Test
    void testSum2() throws EvaluationException {
        var data = Common.getMockDataContext(
                new Transaction(new Amount(2500, Currency.USD), "Test", 1, TransactionDirection.INCOMING),
                new Transaction(new Amount(5000, Currency.USD), "Test", 1, TransactionDirection.INCOMING)
        );

        var config = new SumNode.SumNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        var node = new SumNode(config);

        var result = node.execute(data);

        assertEquals(7500, result.getValue());
        assertEquals(result.getCurrency(), Currency.USD);
    }

    @Test
    void testSum3() throws EvaluationException {
        var data = Common.getMockDataContext(
                new Transaction(new Amount(2500, Currency.USD), "Test", 1, TransactionDirection.INCOMING),
                new Transaction(new Amount(5000, Currency.USD), "Test", 1, TransactionDirection.OUTGOING)
        );

        var config = new SumNode.SumNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        var node = new SumNode(config);

        var result = node.execute(data);

        assertEquals(-2500, result.getValue());
        assertEquals(result.getCurrency(), Currency.USD);
    }

    @Test
    void testSum1NoRespectDirection() throws EvaluationException {
        var data = Common.getMockDataContext(
                new Transaction(new Amount(2500, Currency.EUR), "Test", 1, TransactionDirection.OUTGOING)
        );

        var config = new SumNode.SumNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        config.respectDirection = false;
        var node = new SumNode(config);

        var result = node.execute(data);

        assertEquals(2500, result.getValue());
        assertEquals(result.getCurrency(), Currency.EUR);
    }

    @Test
    void testSum2NoRespectDirection() throws EvaluationException {
        var data = Common.getMockDataContext(
                new Transaction(new Amount(2500, Currency.EUR), "Test", 1, TransactionDirection.OUTGOING),
                new Transaction(new Amount(3000, Currency.EUR), "Test", 1, TransactionDirection.INCOMING)
        );

        var config = new SumNode.SumNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        config.respectDirection = false;
        var node = new SumNode(config);

        var result = node.execute(data);

        assertEquals(5500, result.getValue());
        assertEquals(result.getCurrency(), Currency.EUR);
    }

    @Test
    void testSumException() {
        var data = Common.getMockDataContext(
                new Transaction(new Amount(2500, Currency.EUR), "Test", 1, TransactionDirection.OUTGOING),
                new Transaction(new Amount(3000, Currency.USD), "Test", 1, TransactionDirection.INCOMING)
        );

        var config = new SumNode.SumNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        var node = new SumNode(config);

        assertThrows(EvaluationException.class, () -> {
            node.execute(data);
        }, "Inconsistent currencies");
    }
}
