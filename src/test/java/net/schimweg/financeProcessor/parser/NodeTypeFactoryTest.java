package net.schimweg.financeProcessor.parser;

import net.schimweg.financeProcessor.plugin.NodeTypeFactory;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTypeFactoryTest {
    @Test
    void testNodeTypeFactory() {
        AtomicBoolean called = new AtomicBoolean(false);
        String typeName = "type1";
        String otherTypeName = "type2";
        NodeTypeFactory factory = new NodeTypeFactory();

        // Mock Factory
        factory.addType(typeName, String.class, (o -> {
            called.set(true);
            return null;
        }));

        factory.addType(otherTypeName, Integer.class, (o -> {
            fail();
            return null;
        }));

        var typeInfo = factory.getTypeInformation(typeName);

        assertEquals(typeInfo.getConfigType(), String.class);
        assertNull(typeInfo.getFactory().apply(null));
        assertTrue(called.get());
    }
}
