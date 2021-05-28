package net.schimweg.financeProcessor.plugins.defaultTransformations.filter;

import net.schimweg.financeProcessor.execution.EvaluationException;
import net.schimweg.financeProcessor.model.Amount;
import net.schimweg.financeProcessor.model.Transaction;
import net.schimweg.financeProcessor.model.TransactionDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatcherTest {
    @Test
    void testAmountValueEquals() throws EvaluationException {
        Condition c = new Condition();
        c.field = "amount_value";
        c.numValue = 500L;
        c.type = "equals";
        Matcher m = new Matcher(new Condition[] {c} );

        assertFalse(m.matches(new Transaction(
                new Amount(499L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertTrue(m.matches(new Transaction(
                new Amount(500L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertFalse(m.matches(new Transaction(
                new Amount(501L, "EUR"), "", 0, TransactionDirection.INCOMING)));
    }

    @Test
    void testAmountCurrencyEquals() throws EvaluationException {
        Condition c = new Condition();
        c.field = "amount_currency";
        c.stringValue = "EUR";
        c.type = "equals";
        Matcher m = new Matcher(new Condition[] {c} );


        assertTrue(m.matches(new Transaction(
                new Amount(500L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertFalse(m.matches(new Transaction(
                new Amount(500L, "USD"), "", 0, TransactionDirection.INCOMING)));
    }

    @Test
    void testSubjectEquals() throws EvaluationException {
        Condition c = new Condition();
        c.field = "subject";
        c.stringValue = "My Subject";
        c.type = "equals";
        Matcher m = new Matcher(new Condition[] {c} );


        assertTrue(m.matches(new Transaction(
                new Amount(500L, "EUR"), "My Subject", 0, TransactionDirection.INCOMING)));
        assertFalse(m.matches(new Transaction(
                new Amount(500L, "EUR"), "Not my Subject", 0, TransactionDirection.INCOMING)));
    }

    @Test
    void testCostCenterEquals() throws EvaluationException {
        Condition c = new Condition();
        c.field = "cost_center";
        c.numValue = 500L;
        c.type = "equals";
        Matcher m = new Matcher(new Condition[] {c} );


        assertTrue(m.matches(new Transaction(
                new Amount(500L, "EUR"), "", 500, TransactionDirection.INCOMING)));
        assertFalse(m.matches(new Transaction(
                new Amount(500L, "EUR"), "", 501, TransactionDirection.INCOMING)));
    }

    @Test
    void testDirectionEquals() throws EvaluationException {
        Condition c = new Condition();
        c.field = "direction";
        c.directionValue = TransactionDirection.INCOMING;
        c.type = "equals";
        Matcher m = new Matcher(new Condition[] {c} );


        assertTrue(m.matches(new Transaction(
                new Amount(500L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertFalse(m.matches(new Transaction(
                new Amount(500L, "EUR"), "", 0, TransactionDirection.OUTGOING)));
    }

    @Test
    void testAmountValueLarger() throws EvaluationException {
        Condition c = new Condition();
        c.field = "amount_value";
        c.numValue = 500L;
        c.type = "larger";
        Matcher m = new Matcher(new Condition[] {c} );

        assertFalse(m.matches(new Transaction(
                new Amount(499L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertFalse(m.matches(new Transaction(
                new Amount(500L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertTrue(m.matches(new Transaction(
                new Amount(501L, "EUR"), "", 0, TransactionDirection.INCOMING)));
    }

    @Test
    void testAmountValueLargerEquals() throws EvaluationException {
        Condition c = new Condition();
        c.field = "amount_value";
        c.numValue = 500L;
        c.type = "larger_equals";
        Matcher m = new Matcher(new Condition[] {c} );

        assertFalse(m.matches(new Transaction(
                new Amount(499L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertTrue(m.matches(new Transaction(
                new Amount(500L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertTrue(m.matches(new Transaction(
                new Amount(501L, "EUR"), "", 0, TransactionDirection.INCOMING)));
    }

    @Test
    void testAmountValueSmaller() throws EvaluationException {
        Condition c = new Condition();
        c.field = "amount_value";
        c.numValue = 500L;
        c.type = "smaller";
        Matcher m = new Matcher(new Condition[] {c} );

        assertTrue(m.matches(new Transaction(
                new Amount(499L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertFalse(m.matches(new Transaction(
                new Amount(500L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertFalse(m.matches(new Transaction(
                new Amount(501L, "EUR"), "", 0, TransactionDirection.INCOMING)));
    }

    @Test
    void testAmountValueSmallerEquals() throws EvaluationException {
        Condition c = new Condition();
        c.field = "amount_value";
        c.numValue = 500L;
        c.type = "smaller_equals";
        Matcher m = new Matcher(new Condition[] {c} );

        assertTrue(m.matches(new Transaction(
                new Amount(499L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertTrue(m.matches(new Transaction(
                new Amount(500L, "EUR"), "", 0, TransactionDirection.INCOMING)));
        assertFalse(m.matches(new Transaction(
                new Amount(501L, "EUR"), "", 0, TransactionDirection.INCOMING)));
    }

    @Test
    void testNoValueSpecified() {
        Condition c = new Condition();
        c.field = "amount_value";
        c.type = "equals";

        assertThrows(EvaluationException.class,
                () -> new Matcher(new Condition[] {c} ));
    }

    @Test
    void testWrongField() {
        Condition c = new Condition();
        c.field = "wrong_field";
        c.numValue = 5L;
        c.type = "equals";

        assertThrows(EvaluationException.class,
                () -> new Matcher(new Condition[] {c} ));
    }

    @Test
    void testWrongType() {
        Condition c = new Condition();
        c.field = "amount_value";
        c.numValue = 5L;
        c.type = "wrong_type";

        assertThrows(EvaluationException.class,
                () -> new Matcher(new Condition[] {c} ));
    }

    @Test
    void testWrongValueType() throws EvaluationException {
        Condition c = new Condition();
        c.field = "amount_value";
        c.stringValue = "val";
        c.type = "smaller_equals";
        Matcher m = new Matcher(new Condition[] {c} );

        assertThrows(WrongTypeException.class, () -> m.matches(new Transaction(
                new Amount(501L, "EUR"), "", 0, TransactionDirection.INCOMING)));
    }

    @Test
    void testWrongAccessorType() throws EvaluationException {
        Condition c = new Condition();
        c.field = "amount_currency";
        c.numValue = 500L;
        c.type = "smaller_equals";
        Matcher m = new Matcher(new Condition[] {c} );

        assertThrows(WrongTypeException.class, () -> m.matches(new Transaction(
                new Amount(501L, "EUR"), "", 0, TransactionDirection.INCOMING)));
    }
}
