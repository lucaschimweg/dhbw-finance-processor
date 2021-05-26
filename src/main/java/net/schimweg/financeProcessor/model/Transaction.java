package net.schimweg.financeProcessor.model;

public class Transaction implements MaterializedFinanceObject {
    private final Amount amount;
    private final String subject;
    private final long costCenter;
    private final TransactionDirection direction;

    public Transaction(Amount amount, String subject, long costCenter, TransactionDirection direction) {
        this.amount = amount;
        this.subject = subject;
        this.costCenter = costCenter;
        this.direction = direction;
    }

    public Amount getAmount() {
        return amount;
    }

    public String getSubject() {
        return subject;
    }

    public long getCostCenter() {
        return costCenter;
    }

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
