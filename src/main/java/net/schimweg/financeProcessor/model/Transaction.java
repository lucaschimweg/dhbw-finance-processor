package net.schimweg.financeProcessor.model;

/**
 * A Transaction represents the transfer of money
 */
public class Transaction implements MaterializedFinanceObject {
    private final Amount amount;
    private final String subject;
    private final long costCenter;
    private final TransactionDirection direction;

    /**
     * Creates a new Transaction with the given parameters
     * @param amount The amount transferred
     * @param subject The subject attached to the transaction
     * @param costCenter The cost center the transaction is using
     * @param direction The direction of the transaction
     */
    public Transaction(Amount amount, String subject, long costCenter, TransactionDirection direction) {
        this.amount = amount;
        this.subject = subject;
        this.costCenter = costCenter;
        this.direction = direction;
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
}
