package net.schimweg.financeProcessor.model;

import java.util.Objects;

/**
 * Represents an Amount, containing a value and a currency
 */
public final class Amount implements MaterializedFinanceObject {
    private final long value;
    private final String currency;

    /**
     * Create a new Amount object
     * @param value The value of the amount. In the smallest possible unit (e.g. cents for euros)
     * @param currency The currency of the amount
     */
    public Amount(long value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    /**
     * @return The currency the amount is in
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return The value of the amount. In the smallest possible unit (e.g. cents for euros)
     */
    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                ", currency=" + currency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return value == amount.value && Objects.equals(currency, amount.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }
}
