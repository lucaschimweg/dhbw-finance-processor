package net.schimweg.financeProcessor.model;

public class Amount implements FinanceObject {
    private final long value;
    private final Currency currency;

    public Amount(long value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

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
}
