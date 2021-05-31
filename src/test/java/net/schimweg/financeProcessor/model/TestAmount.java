package net.schimweg.financeProcessor.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestAmount {
    @Test
    void testAmountEquality() {
        Amount a = new Amount(500, "EUR");
        Amount b = new Amount(500, "EUR");
        Amount c = new Amount(500, "USD");
        Amount d = new Amount(1000, "EUR");

        assertEquals(500, a.getValue());
        assertEquals("EUR", a.getCurrency());

        assertEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);

        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a.hashCode(), c.hashCode());
        assertNotEquals(a.hashCode(), d.hashCode());

        assertEquals(a, a);

        assertNotEquals(a, new Object());
        assertNotEquals(a, null);

        assertEquals(a.toString(), b.toString());
        assertNotEquals(a.toString(), c.toString());
        assertNotEquals(a.toString(), d.toString());
    }
}
