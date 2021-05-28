package net.schimweg.financeProcessor.plugins.defaultTransformations;

import net.schimweg.financeProcessor.Common;
import net.schimweg.financeProcessor.execution.EvaluationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountNodeTest {

    @Test
    void testCount0() throws EvaluationException {
        var data = Common.getMockDataContext(0);

        var config = new CountNode.CountNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        var node = new CountNode(config);

        var result = node.execute(data);

        assertEquals(0, result.getValue());
        assertEquals(result.getCurrency(), "");
    }

    @Test
    void testCount1() throws EvaluationException {
        var data = Common.getMockDataContext(1);

        var config = new CountNode.CountNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        var node = new CountNode(config);

        var result = node.execute(data);

        assertEquals(1, result.getValue());
        assertEquals(result.getCurrency(), "");
    }

    @Test
    void testCount2() throws EvaluationException {
        var data = Common.getMockDataContext(2);

        var config = new CountNode.CountNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        var node = new CountNode(config);

        var result = node.execute(data);

        assertEquals(2, result.getValue());
        assertEquals(result.getCurrency(), "");
    }

    @Test
    void testCount10() throws EvaluationException {
        var data = Common.getMockDataContext(10);

        var config = new CountNode.CountNodeConfig();
        config.source = new AllTransactionsNode(Common.MockDataContextName);
        var node = new CountNode(config);

        var result = node.execute(data);

        assertEquals(10, result.getValue());
        assertEquals(result.getCurrency(), "");
    }
}
