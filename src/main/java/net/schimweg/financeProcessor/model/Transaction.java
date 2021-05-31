package net.schimweg.financeProcessor.model;

import java.util.Objects;

/**
 * A Transaction represents the transfer of money
 */
public final class Transaction implements MaterializedFinanceObject {
    private final Long id;
    private final Amount amount;
    private final String subject;
    private final long costCenter;
    private final TransactionDirection direction;

    /**
     * Creates a new Transaction with the given parameters
     * @param id The transaction's ID
     * @param amount The amount transferred
     * @param subject The subject attached to the transaction
     * @param costCenter The cost center the transaction is using
     * @param direction The direction of the transaction
     */
    public Transaction(Long id, Amount amount, String subject, long costCenter, TransactionDirection direction) {
        this.id = id;
        this.amount = amount;
        this.subject = subject;
        this.costCenter = costCenter;
        this.direction = direction;
    }

    /**
     * @return The Transaction's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @return The amount transferred
     */
    public Amount getAmount() {
        return amount;
    }

    /**
     * @return The subject attached to the transaction
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return The cost center the transaction is using
     */
    public long getCostCenter() {
        return costCenter;
    }

    /**
     * @return The direction of the transaction
     */
    public TransactionDirection getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", subject='" + subject + '\'' +
                ", costCenter=" + costCenter +
                ", direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return that.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
