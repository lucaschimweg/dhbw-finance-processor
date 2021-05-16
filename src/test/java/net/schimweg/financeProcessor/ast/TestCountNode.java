package net.schimweg.financeProcessor.ast;

import net.schimweg.financeProcessor.Common;
import net.schimweg.financeProcessor.model.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCountNode {

    @Test
    void testName() {

        var config = new CountNode.CountNodeConfig();
        config.source = new AllTransactionsNode();
        var node = new CountNode(config);

        assertEquals("COUNT", node.name());
    }

    @Test
    void testCount0() {
        var data = Common.getDummyDataContext(0);

        var config = new CountNode.CountNodeConfig();
        config.source = new AllTransactionsNode();
        var node = new CountNode(config);

        var result = node.execute(data);

        assertEquals(0, result.getValue());
        assertEquals(result.getCurrency(), Currency.NONE);
    }

    @Test
    void testCount1() {
        var data = Common.getDummyDataContext(1);

        var config = new CountNode.CountNodeConfig();
        config.source = new AllTransactionsNode();
        var node = new CountNode(config);

        var result = node.execute(data);

        assertEquals(1, result.getValue());
        assertEquals(result.getCurrency(), Currency.NONE);
    }

    @Test
    void testCount2() {
        var data = Common.getDummyDataContext(2);

        var config = new CountNode.CountNodeConfig();
        config.source = new AllTransactionsNode();
        var node = new CountNode(config);

        var result = node.execute(data);

        assertEquals(2, result.getValue());
        assertEquals(result.getCurrency(), Currency.NONE);
    }

    @Test
    void testCount10() {
        var data = Common.getDummyDataContext(10);

        var config = new CountNode.CountNodeConfig();
        config.source = new AllTransactionsNode();
        var node = new CountNode(config);

        var result = node.execute(data);

        assertEquals(10, result.getValue());
        assertEquals(result.getCurrency(), Currency.NONE);
    }
}
