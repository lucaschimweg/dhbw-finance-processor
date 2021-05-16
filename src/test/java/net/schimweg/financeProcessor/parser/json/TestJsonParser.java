package net.schimweg.financeProcessor.parser.json;

import net.schimweg.financeProcessor.ast.AllTransactionsNode;
import net.schimweg.financeProcessor.ast.FilterNode;
import net.schimweg.financeProcessor.ast.SumNode;
import net.schimweg.financeProcessor.ast.UnionNode;
import net.schimweg.financeProcessor.parser.NodeTypeFactory;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonParser {
    @Test
    public void testParserSimple() {
        assertTrue(true);
    }

    @Test
    public void test2() {
        assertFalse(false);
    }

}
